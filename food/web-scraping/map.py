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
csv_file_path = './food/web-scraping/FoodSearchData.csv'
output_csv_file = './food/web-scraping/관광지별음식점.csv'
output_csv_file_v1 = './food/web-scraping/관광지별음식점메뉴.csv'

print(os.getcwd())
# CSV 파일을 pandas로 읽기
df = pd.read_csv(csv_file_path, encoding='cp949')

''' 0.1.driver 설정 '''
chrome_options = Options()
chrome_options.add_experimental_option("detach",True)
chrome_options.add_argument('--headless')
chrome_options.add_argument('--window-size=1920,1080')
chrome_options.add_argument("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")



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

# frame 변경 메소드
def switch_frame(frame):
    browser.switch_to.default_content()  # frame 초기화
    browser.switch_to.frame(frame)  # frame 변경

# 페이지 다운
def page_down(num):
    body = browser.find_element(By.CSS_SELECTOR, 'body')
    # body.click()
    for i in range(num):
        body.send_keys(Keys.PAGE_DOWN)

def page_end():
    #page_down 누르면 광고나 내가 원하는 곳을 클릭하지 않기 때문에 새로 만듦
    try:
        i = 1
        while True:
            nth_value = 10 * i
            element = browser.find_element(By.CSS_SELECTOR, f'li.UEzoS:nth-child({nth_value})')
            browser.execute_script("arguments[0].scrollIntoView();", element)
            i += 1

    except Exception as e:
        # print(e)
        print()

 # 크롤링
def scraping(iidx):
    food_name = ''
    food_type = ''
    telephone = ''
    star = 0
    real_view = 0
    blog_view = 0
    food_menu = {}
         
    ''' (1) 기본 정보 가져오기 '''
    try:    
        food_name = browser.find_element(By.CSS_SELECTOR,"span.Fc1rA").text  #음식점명
        print("음식점명 : ",food_name)

        food_type = browser.find_element(By.CSS_SELECTOR,"span.DJJvD").text #음식점 카테고리
        print("음식점 카테고리 : ",food_type)

        telephone = browser.find_element(By.CSS_SELECTOR,"span.xlx7Q").text #전화번호
        print("전화번호 : ",telephone)
    except Exception as e:
        print(e)

    ''' (2) 리뷰 수 가져오기 '''
    try :  
        lsts = browser.find_elements(By.CSS_SELECTOR,"span.PXMot") #방문자 리뷰, 블로그리뷰  

        if (len(lsts) == 2):
            try:
                real_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(1)>a").text
            except :
                print("방문자 0명")
            try :
                blog_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(2)>a").text
            except :
                print("블로그 후기 0명")

        elif (len(lsts) == 3) : #별까지 있는 경우 
            star = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(1)").text.split("\n")[1]
            print("별점 : ", star)
            try:
                real_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(2)>a").text
            except :
                print("방문자 0명")
            try :
                blog_view = browser.find_element(By.CSS_SELECTOR,"div.dAsGb>span:nth-of-type(3)>a").text
            except :
                print("블로그 후기 0명")

        if(type(real_view) is str and "," in real_view) :
            real_view = real_view.replace(",","")
            real_view = int(real_view.split(" ")[1])
            print("방문자 리뷰 : ",real_view)
        else :
            real_view = int(real_view.split(" ")[1])
            print("방문자 리뷰 : ",real_view)
        if(type(blog_view) is str and "," in blog_view):
            blog_view = blog_view.replace(",","")
            blog_view = int(blog_view.split(" ")[1])
            print("블로그 리뷰 : ",blog_view)
        else :
            blog_view = int(blog_view.split(" ")[1])
            print("블로그 리뷰 : ",blog_view)

    except Exception as e:
        print(e)
        print('[리뷰 수집 Error...]')

    ''' (4) 메뉴 가지고 오기 '''
    try:
        #메뉴 추출하기 
        menu_list = browser.find_elements(By.CSS_SELECTOR,"div.flicking-camera>a")

        #nav에서 길이가 4 이하면 메뉴가 2번째에 위치, 4초과면 3번째에 위치한다. 
        ismenu = False
        for i in range(len(menu_list)): 
            if menu_list.__getitem__(i).text == '메뉴':
                menu_btn = menu_list.__getitem__(i)
                menu_btn.click()
                ismenu = True
                break
                

        #메뉴 페이지 더보기 처리
        time.sleep(0.5) 
        page_down(20)  

        if ismenu: #메뉴가 아예 없다면 돌을 필요 없음
            try:
                print("더보기 버튼 개수 : ", len(browser.find_elements(By.CSS_SELECTOR,'div.lfH3O > a.fvwqf')))
                if (len(browser.find_elements(By.CSS_SELECTOR,'div.lfH3O > a.fvwqf'))>0):
                    while True:
                        more = browser.find_elements(By.CSS_SELECTOR,"div.lfH3O > a.fvwqf")
                        if (len(more) == 0) :
                            print("[더보기 버튼 ❌...]")
                            break

                        for idx in range(len(more)): 
                            browser.execute_script("arguments[0].click();", more[idx])
                            print("[[더보기 버튼 Click...]]")

            except Exception as e:
                print(e)
                print('[더보기 버튼 Error...]')
    
            menul = []
            flag = False

            if len(browser.find_elements(By.CSS_SELECTOR,"li.order_list_item")) > 0 :
                menul = browser.find_elements(By.CSS_SELECTOR,"li.order_list_item")
                flag = True
            else:
                menul = browser.find_elements(By.CSS_SELECTOR,"li.E2jtL")

            for idx in range(len(menul)): 
                if(flag) :
                    menu_name = menul[idx].find_element(By.CSS_SELECTOR, ".info_detail > div.tit").text
                    # print("메뉴 이름 : ",menu_name)
                    menu_price = menul[idx].find_element(By.CSS_SELECTOR, ".price").text
                    # print("메뉴 가격 : ",menu_price)
                else :
                    menu_name = menul[idx].find_element(By.CSS_SELECTOR, ".yQlqY > span.lPzHi").text
                    # print("메뉴 이름 : ",menu_name)
                    menu_price = menul[idx].find_element(By.CSS_SELECTOR, ".GXS1X").text
                    # print("메뉴 가격 : ",menu_price)
        
                food_menu[menu_name] = menu_price
                #food_menu용 엑셀
                food_menu_dict_temp = {
                    "idx" : iidx,
                    "content_id" : id,
                    "menu_name" : menu_name,
                    "menu_price" : menu_price
                }
                food_menu_dict.append(food_menu_dict_temp)

        food_menu = ', '.join([f'{menu}: {price}' for menu, price in food_menu.items()])
    
    except Exception as e:
        print(e)
    
    '''데이터 저장하기'''
    dict_temp = {
        "idx" : iidx,
        "content_id" : id,
        'name': food_name,
        'food_type': food_type,
        'road_address': road_address,
        'jibun_address': jibun_address,
        'telephone' : telephone,
        'star' : float(star),
        'real_view' : real_view, 
        'blog_view' : blog_view,
        'food_menu' : food_menu,
        'x':x,
        'y':y
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
food_menu_dict = []

iidx = 0
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
        # if index == 5: break

        # (1) 검색창에 검색하기
        browser.switch_to.default_content() #다시 검색창을 찾아야 해서 초기화 
        search = browser.find_element(By.CSS_SELECTOR, 'div.input_box > input.input_search')
        search.click()
        print("[키워드 검색함...]")
        search.clear() #검색어 초기화
        search.send_keys(key_word) #검색어 입력 
        time.sleep(0.5)
        search.send_keys(Keys.ENTER) #엔터버튼 누르기 
        time.sleep(0.1)

        try :
            #검색 시 바로 나옴 
            print("검색시 바로 나옴 시작")
            # 상세 프레임으로 이동
            switch_frame('entryIframe')
            scraping(iidx)
            print("검색시 바로 나옴 끝")

        except Exception as e:
            #검색해도 안 나오거나 여러 개 검색됨 
            print(e)
            switch_frame('searchIframe') 
            food_list = browser.find_elements(By.CSS_SELECTOR, 'li.UEzoS') # 음식점 리스트
            print(len(food_list))
            if len(food_list) != 1 :
                print("이번 키워드 pass")
                continue
            else:
                try : #한 개만 검색된다면 돌리기 
                    #클릭해서 크롤링
                    name = food_list[0].find_element(By.CSS_SELECTOR,"span.place_bluelink")
                    name.click()
                    time.sleep(1)
                    print("[[이름 클릭...]]")
                    
                    #크롤링 def 
                    print("크롤링")
                    switch_frame('entryIframe')
                    scraping(iidx)
            
                except Exception as e:
                    print(e)
        finally:
            iidx+=1
            print(f'[{key_word}데이터 수집 완료]\n소요 시간 :', time.time() - startone)


except Exception as e:
            print("----")
            print(e)
 
finally:
    print('[데이터 수집 완료]\n소요 시간 :', time.time() - start)
    browser.quit()  # 작업이 끝나면 창을 닫는다.

    result_df = pd.DataFrame(food_dict)
    result_df.to_csv(output_csv_file, index=False, encoding='utf-8-sig')
    result_df_v1 = pd.DataFrame(food_menu_dict)
    result_df_v1.to_csv(output_csv_file_v1, index=False, encoding='utf-8-sig')
       
            


      
     






