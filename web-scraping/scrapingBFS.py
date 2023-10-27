from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from urllib.parse import unquote
from pymongo import MongoClient
from koreanCheck import remove_non_korean
import asyncio
from MongoDBConnector import MongoDBConnector
import time 
import chromedriver_autoinstaller
from text_similarity import textSimilarity
import pymysql



options = Options()
options.add_argument('--no-sandbox')
options.add_argument('--disable-dev-shm-usage')
options.add_argument('--window-size=1920,1080') # 윈도우 창 설정
# UserAgent값을 바꿔줌(headless 탐지 막기)
#options.add_argument("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
# options.add_argument("--start-maximized")
# options.add_experimental_option("detach", True)
options.add_argument('--headless')  # headless 모드 활성화 # 백 그라운드에서 실행 
service = Service(executable_path=ChromeDriverManager().install())
wd = webdriver.Chrome(service=service, options=options)


connector = MongoDBConnector()

mysqlConnector = pymysql.connect(host='k9b205.p.ssafy.io', user='b205', password='9gi_ssafy_final', db='b205', charset='utf8')
cur = mysqlConnector.cursor()
sql = 'select title from attraction_info'

result = cur.execute(sql)
attractionArr = cur.fetchall()


# 여행지로부터 키워드 추출
# BFS 알고리즘 사용하여 관련있는 page 수집 후 mongoDB에 저장하는 과정 

# parameter:
#           attractionName : 관광지 이름
def getKeyWord(attractionName):
    
    # URI BFS위한 큐와 뎁스
    depth = 0
    queue = []
    
    queue.append([f"https://namu.wiki/w/{attractionName}", depth, attractionName])
    queue.append([f"https://namu.wiki/Search?q={attractionName}", depth, attractionName])
    # wd.get(f"https://namu.wiki/w/{attractionName}")
    # if wd.find_element(By.ID, "app").text.find("해당 문서를 찾을 수 없습니다."):
    #     queue.append([f"https://namu.wiki/Search?q={attractionName}", depth, attractionName])
    # else:
    #     queue.append([f"https://namu.wiki/w/{attractionName}", depth, attractionName])

    #BFS 방문 체크
    visited = {} # URI 방문 체크
    visitedName = set() # 키워드 방문체크
    visited[f"https://namu.wiki/w/{attractionName}"] = 1
    visited[f"https://namu.wiki/Search?q={attractionName}"] = 1
    visitedName.add(attractionName)

    #BFS 연산
    while queue:
        node = queue.pop()
        url, curDepth, name = node #uri, BFS depth, 키워드 이름 순

        # html fetch
        wd.get(url)
        totalHTML = wd.find_element(By.ID, "app") #uri 불러온 후 ID tag의 web element 저장
      
        time.sleep(2)
        name = remove_non_korean(name) # 이름에서 한글 제외한 내용 정규식으로 제거

        if name == "": # 이름 비어있으면 continue
            continue
        
        # div에서 text 추출 후 mongoDB에 저장
        divList = totalHTML.find_element(By.TAG_NAME, 'div')
        connector.insert_data({
            "attraction":attractionName, #Root 관광지 이름 저장
            "name":name, #키워드 이름 저장
            "depth":depth, #BFS depth 저장
            "html":divList.text #html text 저장
        })
        
        #다음 BFS queue에 저장할 URI 파싱
        hrefs = totalHTML.find_elements(By.TAG_NAME, "a")
   
        #파싱한 URI를 queue에 push
        for href in hrefs:
            nxtUrl = href.get_attribute("href")
            if nxtUrl is None:
                continue
            nxtName = nxtUrl[20:]

            # 다음 키워드 인코딩
            nxtName = unquote(nxtName, encoding='utf-8')

            #한글 제외한 내용 제거
            nxtName = remove_non_korean(nxtName)
            if nxtName == "":
                continue
            
            # BFS 방문 체크. 큐에 넣는 조건 본다.
            if nxtUrl == None or curDepth == 1 or nxtUrl in visited or nxtName in visitedName:
                continue
            if nxtUrl[0:20] != "https://namu.wiki/w/" or textSimilarity(attractionName, nxtName) < 0.5: 
                # URI에서 키워드 아닌 부분 제거 후 관광지 이름과 다음으로 넘어갈 키워드 유사도 계산한다.
                # 계산한 값이 0.5를 넘어가면 BFS 탐색범위에 들어간다.
                continue
           
            queue.append([nxtUrl, curDepth + 1, nxtName])
            visited[nxtUrl] = 1
            visitedName.add(nxtName)

for index, title in attractionArr:
    attraction_title = remove_non_korean(title[0])
    getKeyWord(attraction_title)
    with open('attraction_index_numbers.txt', 'w', encoding='utf-8') as txt_file:
        txt_file.write(f'{index}\n')