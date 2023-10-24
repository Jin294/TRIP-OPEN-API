from selenium import webdriver
from selenium.webdriver.common.keys import Keys

from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from urllib.parse import unquote
from pymongo import MongoClient

from koreanCheck import remove_non_korean
from mongoConf import saveHTML

import time 

import chromedriver_autoinstaller

from text_similarity import textSimilarity

# print(chromedriver_autoinstaller.get_chrome_version()) # 크롬드라이버 버전확인

mongodb_URI = "mongodb+srv://S09P31B205:z5HxUpl4gB@ssafy.ngivl.mongodb.net/S09B31B205?authSource=admin"
client = MongoClient(mongodb_URI)
db = client["S09P31B205"]
collection = db["wiki_HTML"]


def getKeyWord(attractionName):
    options = Options()
    options.add_experimental_option("detach", True)
    service = Service(ChromeDriverManager().install())
    wd = webdriver.Chrome(service=service, options=options)

    depth = 0
    queue = []
    queue.append([f"https://namu.wiki/w/{attractionName}", depth, attractionName])

    visited = {}
    visitedName = set()
    visited[f"https://namu.wiki/w/{attractionName}"] = 1
    visitedName.add(attractionName)
    while queue:
        node = queue.pop()
        url, curDepth, name = node

        wd.get(url)
        totalHTML = wd.find_element(By.ID, "app")
        time.sleep(2)
        name = remove_non_korean(name)

        if name == "":
            continue
        #mongo db에 페이지 저장
        # 저장 로직 추후 작성
        # saveHTML(attractionName, name, curDepth, totalHTML)

        
        hrefs = totalHTML.find_elements(By.TAG_NAME, "a")
   
        for href in hrefs:
            nxtUrl = href.get_attribute("href")
            if nxtUrl is None:
                continue

            nxtName = nxtUrl[20:]

            nxtName = unquote(nxtName, encoding='utf-8')
            nxtName = remove_non_korean(nxtName)
            if nxtName == "":
                continue

            print(f"from : {name}, to : {nxtName}")
            if nxtUrl == None or curDepth == 1 or nxtUrl in visited or nxtName in visitedName:
                continue
            if nxtUrl[0:20] != "https://namu.wiki/w/" or textSimilarity(name, nxtName) < 0.5:
                continue
            # print(href.get_attribute("href"))
            print("visited success" + '\n\n')

            queue.append([nxtUrl, curDepth + 1, nxtName])
            visited[nxtUrl] = 1
            visitedName.add(nxtName)

getKeyWord("해운대")