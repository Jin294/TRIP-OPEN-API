from selenium import webdriver
from selenium.webdriver.common.keys import Keys

from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager

import chromedriver_autoinstaller
print(chromedriver_autoinstaller.get_chrome_version()) # 크롬드라이버 버전확인


def getKeyWord(attractionName):
    options = Options()
    options.add_experimental_option("detach", True)
    service = Service(ChromeDriverManager().install())
    wd = webdriver.Chrome(service=service, options=options)

    depth = 0
    queue = []
    queue.append([f"https://namu.wiki/w/{attractionName}", depth])

    visited = {}
    visited[f"https://namu.wiki/w/{attractionName}"] = 1

    while queue:
        node = queue.pop()
        url, curDepth = node
        print(curDepth)


        wd.get(url)
        totalHTML = wd.find_element(By.ID, "app")
        #mongo db에 페이지 저장
        # 저장 로직 추후 작성

        hrefs = totalHTML.find_elements(By.TAG_NAME, "a")
   
        for href in hrefs:
            nxtUrl = href.get_attribute("href")
            if nxtUrl == None or curDepth == 1 or nxtUrl in visited:
                continue
            if nxtUrl[0:20] != "https://namu.wiki/w/":
                continue

            # print(href.get_attribute("href"))
            queue.append([nxtUrl, curDepth + 1])
            visited[nxtUrl] = 1


# s = input("검색 키워드를 입력하세요 : ")
getKeyWord("해운대")

