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



    wd.get(f"https://namu.wiki/w/{attractionName}")
    totalHTML = wd.find_element(By.ID, "app")

    # print(totalHTML.get_attribute("innerHTML"))

    hrefs = totalHTML.find_elements(By.TAG_NAME, "a")
    queue = list()
    for href in hrefs:
        # print(href.get_attribute("href"))
        if(href.get_attribute("href") == "None"):
            continue
        queue.append(href.get_attribute("href"))

    print("close")


# s = input("검색 키워드를 입력하세요 : ")
getKeyWord("해운대")

