# csv 파일을 다루기 위한 pandas
import pandas as pd
# selenium의 webdriver를 사용하기 위한 import
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from webdriver_manager.chrome import ChromeDriverManager
# selenium으로 키를 조작하기 위한 import
from selenium.webdriver.common.keys import Keys
# 페이지 로딩을 기다리는데에 사용할 time 모듈 import
import time
# 크롬 드라이버 autoinstaller
import chromedriver_autoinstaller
# print(chromedriver_autoinstaller.get_chrome_version()) # 크롬드라이버 버전확인
chrome_ver = chromedriver_autoinstaller.get_chrome_version()

# 크롬드라이버 실행
# driver = webdriver.Chrome() 
options = Options()
options.add_experimental_option("detach", True)
service = Service(ChromeDriverManager().install())
driver = webdriver.Chrome(service=service, options=options)

# CSV 파일 경로
csv_file_path = '판팡지주소csv.csv'
output_csv_file = '관광지별숙소.csv'

# CSV 파일을 pandas로 읽기
df = pd.read_csv(csv_file_path, encoding='utf-8')

# 결과를 저장할 리스트
results = []

# 웹페이지 주소
url = 'https://www.goodchoice.kr/' # 여기어때

# CSV 파일에서 검색어 및 contentId 읽어오기
for index, row in df.iterrows():
    content_id = row['contentId']
    add1 = row['add1']
    add2 = row['add2']

    driver.get(url)
    time.sleep(3)
    # 검색창을 여는 버튼을 찾아 # 검색어 창
    search_open_btn = driver.find_element(By.CSS_SELECTOR, 'button.btn_srch.srch_open')
    search_box = driver.find_element(By.XPATH, '//*[@id="keyword"]')

    # 먼저 add1로 검색 시도
    search_box.clear()
    search_box.send_keys(add1)
    search_box.send_keys(Keys.RETURN)
    time.sleep(3)

    # 검색 결과 확인 
    search_results = driver.find_elements(By.CLASS_NAME, 'goodChoiceItem')
    if search_results:
        # 검색 결과가 있을 때 contentId 및 숙소 정보 추출
        for result in search_results:
            room_name = result.find_element(By.CLASS_NAME, 'item_desc').text
            results.append({'contentId': content_id, '숙소': room_name})
    else:
        # add1 검색 결과가 없을 때 add2로 검색 시도
        search_box.clear()
        search_box.send_keys(add2)
        search_box.send_keys(Keys.RETURN)
        time.sleep(3)

        # 검색 결과 확인
        search_results = driver.find_elements(By.CLASS_NAME, 'goodChoiceItem')
        if search_results:
            # 검색 결과가 있을 때 contentId 및 숙소 정보 추출
            for result in search_results:
                room_name = result.find_element(By.CLASS_NAME, 'item_desc').text
                results.append({'contentId': content_id, '숙소': room_name})

# 검색 결과를 pandas DataFrame으로 만들고 CSV 파일에 저장
result_df = pd.DataFrame(results)
result_df.to_csv(output_csv_file, index=False, encoding='utf-8')

# 브라우저 종료
driver.quit()