# pip install webdriver_manager
# pip install selenium==4.10
from webdriver_manager.chrome import ChromeDriverManager

from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

import pandas as pd
import time
from bs4 import BeautifulSoup
import os

''' 0.0.csv import & export '''
# CSV 파일 경로
csv_file_path = 'FoodSearchData.csv'
output_csv_file = '관광지별음식점.csv'
output_csv_file_v1 = '관광지별음식점메뉴.csv'

# print(os.getcwd())
# CSV 파일을 pandas로 읽기
df = pd.read_csv(csv_file_path, encoding='cp949')

''' 0.1.driver 설정 '''
chrome_options = Options()
chrome_options.add_experimental_option("detach",True)
chrome_options.add_argument('--headless')
chrome_options.add_argument('--window-size=1920,1080')
chrome_options.add_argument("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
chrome_options.add_argument('--no-sandbox')
chrome_options.add_argument('--disable-dev-shm-usage')


service = Service(executable_path=ChromeDriverManager().install())  # Chrome driver 자동 업데이트
browser = webdriver.Chrome(service=service, options=chrome_options)


browser.get("https://map.naver.com")
browser.implicitly_wait(10)
browser.execute_script("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})")
browser.execute_script("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5]}})")

''' 0.2.함수'''
# css 찾을때 까지 10초대기
def time_wait(num, code):
    try:
        wait = WebDriverWait(browser, num).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, code)))
    except:
        print(code, '태그를 찾지 못하였습니다.')
        browser.quit()
    return wait

def time_wait_frame(num, code):
    try:
        wait = WebDriverWait(browser, num)
        wait.until(EC.frame_to_be_available_and_switch_to_it(code))
    except Exception as e:
        print(e)
        print("frame 못 찾음")

# frame 변경 메소드
def switch_frame(frame):
    browser.switch_to.default_content()  # frame 초기화
    browser.switch_to.frame(frame)  # frame 변경

 # 크롤링
def scraping(iidx):
    food_name = ''
    food_type = ''
    telephone = ''
    star = 0
    real_view = 0
    blog_view = 0
         
    ''' (1) 기본 정보 가져오기 '''
    try:    
        food_name = browser.find_element(By.CSS_SELECTOR,"span.Fc1rA").text  #음식점명
        # print("음식점명 : ",food_name)

        food_type = browser.find_element(By.CSS_SELECTOR,"span.DJJvD").text #음식점 카테고리
        # print("음식점 카테고리 : ",food_type)

        telephone = browser.find_element(By.CSS_SELECTOR,"span.xlx7Q").text #전화번호
        # print("전화번호 : ",telephone)
    except Exception as e:
        print(e)

    ''' (2) 리뷰 수 가져오기 '''
    try :  
        lsts = browser.find_elements(By.CSS_SELECTOR,"span.PXMot") #방문자 리뷰, 블로그리뷰  

        if (len(lsts) == 2):
            # try:
            #     real_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(1)>a").text
            # except :
            #     print("방문자 0명")
            # try :
            #     blog_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(2)>a").text
            # except :
            #     print("블로그 후기 0명")

            real_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(1)>a").text
            blog_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(2)>a").text

        elif (len(lsts) == 3) : #별까지 있는 경우 
            # star = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(1)").text.split("\n")[1]
            # # print("별점 : ", star)
            # try:
            #     real_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(2)>a").text
            # except :
            #     print("방문자 0명")
            # try :
            #     blog_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(3)>a").text
            # except :
            #     print("블로그 후기 0명")
            star = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(1)").text.split("\n")[1]
            real_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(2)>a").text
            blog_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(3)>a").text

        if(type(real_view) is str and "," in real_view) :
            real_view = int(real_view.replace(",","").split(" ")[1])
            # print("방문자 리뷰 : ",real_view)
        else :
            real_view = int(real_view.split(" ")[1])
            # print("방문자 리뷰 : ",real_view)
        if(type(blog_view) is str and "," in blog_view):
            blog_view = int(blog_view.replace(",","").split(" ")[1])
            # print("블로그 리뷰 : ",blog_view)
        else :
            blog_view = int(blog_view.split(" ")[1])
            # print("블로그 리뷰 : ",blog_view)

    except Exception as e:
        print(e)
        print('[리뷰 수집 Error...]')

    
    '''데이터 저장하기'''
    dict_temp = {
        "food_idx" : iidx,
        "food_id" : id,
        'food_name': food_name,
        'food_type': food_type,
        'food_addr1': road_address,
        'food_addr2': jibun_address,
        'food_tel' : telephone,
        'food_star' : float(star),
        'food_realview' : real_view, 
        'food_blogview' : blog_view,
        'food_longtitude':x,
        'food_latitude':y
        }
    food_dict.append(dict_temp)
    print(f'{food_name} ...완료')
    print(f'[{key_word} 관련 음식점 검색 ...완료]')

    # TXT 파일에 index 번호 입력
    with open('index_numbers.txt', 'w', encoding='utf-8') as txt_file:
        txt_file.write(f'{index}\n')


''' 1.0.크롤링 시작'''
# 시작시간
start = time.time()
print('[크롤링 시작...]')

# dictionary 생성
food_dict = []

iidx = 0
header_df = pd.DataFrame(columns=['food_idx', 'food_id', 'food_name','food_type','food_addr1','food_addr2','food_tel','food_star','food_realview','food_blogview','food_longtitude','food_latitude'])
header_df.to_csv(output_csv_file, mode='w', index=False, encoding='utf-8-sig')

try:
    for index, row in df.iterrows():
        startone = time.time()
        id = row['번호']
        key_word = row['지역']+" "+row['사업장명']
        x = row['좌표정보(x)']
        y = row['좌표정보(y)']
        road_address = row['도로명전체주소']
        jibun_address = row['소재지전체주소']

        # if index == 200: break
        if index == 5: break

        # (1) 검색창에 검색하기
        browser.switch_to.default_content() #다시 검색창을 찾아야 해서 초기화 
        search = browser.find_element(By.CSS_SELECTOR, 'div.input_box > input.input_search')
        search.click()
        print("[키워드 검색함...]")
        search.clear() #검색어 초기화
        search.send_keys(key_word) #검색어 입력 
        search.send_keys(Keys.ENTER) #엔터버튼 누르기 
        time.sleep(0.1)

        try :
            #검색 시 바로 나옴 
            print("검색시 바로 나옴..시작")
            # 상세 프레임으로 이동
            browser.switch_to.frame('entryIframe')
            # switch_frame('entryIframe')
            print('entryIframe 변경')
            scraping(iidx)
            iidx+=1
            print("검색시 바로 나옴..끝")

        except Exception as e:
            #검색해도 안 나오거나 여러 개 검색됨 
            print(e)
            # time.sleep(3)
            browser.switch_to.frame('searchIframe')
            # switch_frame('searchIframe') 
            print("searchIframe 변경")
            food_list = browser.find_elements(By.CSS_SELECTOR, 'li.UEzoS') # 음식점 리스트
            if len(food_list) != 1 :
                print("이번 키워드 pass")
                continue
            else:
                try : #한 개만 검색된다면 돌리기 
                    #클릭해서 크롤링
                    name = food_list[0].find_element(By.CSS_SELECTOR,"span.place_bluelink")
                    name.click()
                    print("[[이름 클릭...]]")
                    
                    #크롤링 def 
                    # browser.switch_to.frame('entryIframe')
                    switch_frame('entryIframe')
                    scraping(iidx)
                    iidx+=1
                except Exception as e:
                    print(e)
        finally:
            result_df = pd.DataFrame(food_dict)
            result_df.to_csv(output_csv_file, mode='a', header=False, index=False, encoding='utf-8-sig')

            food_dict = []
        
            print(f'[{key_word}데이터 수집 완료]\n소요 시간 :', time.time() - startone)


except Exception as e:
            print("----")
            print(e)
 
finally:
    print('[데이터 수집 완료]\n소요 시간 :', time.time() - start)
    browser.quit()  # 작업이 끝나면 창을 닫는다.