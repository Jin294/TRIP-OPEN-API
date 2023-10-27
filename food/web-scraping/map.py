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
csv_file_path = './food/web-scraping/searchData_new.csv'
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

''' 1.0.크롤링 시작'''
# 시작시간
start = time.time()
print('[크롤링 시작...]')

# dictionary 생성
food_dict = []
food_menu_dict = []

iidx = 0
for index, row in df.iterrows():
    id = row['content_id']
    key_word = row['title']

    # if index == 200: break
    if index == 2: break

    # (1) 검색창에 검색하기
    browser.switch_to.default_content() #다시 검색창을 찾아야 해서 초기화 
    search = browser.find_element(By.CSS_SELECTOR, 'div.input_box > input.input_search')
    search.click()
    print("[키워드 검색함...]")
    search.clear() #검색어 초기화
    search.send_keys(key_word+" 맛집") #검색어 입력 
    time.sleep(0.5)
    search.send_keys(Keys.ENTER) #엔터버튼 누르기 
    time.sleep(2)

    # (2) 목록 페이지로 이동
    switch_frame('searchIframe') # iframe 안으로 들어가기 # frame 변경

    # food_list = browser.find_elements(By.CSS_SELECTOR, 'li.UEzoS') # 음식점 리스트
    next_btn_list = browser.find_elements(By.CSS_SELECTOR, '.zRM9F> a') # 페이지 리스트 #이전버튼, 1,2....이후버튼 

    if len(next_btn_list) == 0 : #검색 결과가 없을 경우 다음 loop로 이동 
        print(f'[{key_word} 관련 업체 없어서 종료...]')
        continue 

    # 크롤링
    while True:
        food_list = browser.find_elements(By.CSS_SELECTOR, 'li.UEzoS') # 음식점 리스트
        if len(food_list) > 10 : 
            page_end() #목록 페이지 다 로딩하기 위해서 
        names = browser.find_elements(By.CSS_SELECTOR, '.TYaxT') 

        for data in range(len(food_list)):  # 음식점 리스트 만큼
            switch_frame('searchIframe') # 상세 페이지로 들어가기 때문에 한 번 다시 초기화해주어야함
            food_name = ''
            food_type = ''
            road_address = ''
            jibun_address = ''
            telephone = ''
            star = 0
            real_view = 0
            blog_view = 0
            food_menu = {}
            
            print("음식점 리스트 idx : ",data)  #data는 index 
            time.sleep(1)
            iidx += 1
            # print(iidx)   
        
            #광고 없애기 
            if (len(food_list[data].find_elements(By.CSS_SELECTOR,'svg.dPXjn'))==0):     
               
                try:
                    # 상세 정보 클릭해서 이동 
                    name = food_list[data].find_element(By.CSS_SELECTOR,"span.place_bluelink")
                    name.click()
                    print("[[이름 클릭...]]")
                    time.sleep(0.5)

                    # 상세 프레임으로 이동
                    switch_frame('entryIframe')

                    ''' (1) 기본 정보 가져오기 '''
                    food_name = browser.find_element(By.CSS_SELECTOR,"span.Fc1rA").text  #음식점명
                    print("음식점명 : ",food_name)

                    food_type = browser.find_element(By.CSS_SELECTOR,"span.DJJvD").text #음식점 카테고리
                    print("음식점 카테고리 : ",food_type)

                    telephone = browser.find_element(By.CSS_SELECTOR,"span.xlx7Q").text #전화번호
                    print("전화번호 : ",telephone)

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

                    ''' (3) 주소 가지고 오기 '''
                    try : 
                        # 주소 버튼 누르기
                        address_buttons = browser.find_element(By.CSS_SELECTOR, '.vV_z_ > a')
                        address_buttons.click()
                        print("[[주소 버튼 Click...]]")

                        # 로딩 기다리기
                        time.sleep(1)

                        # (1) 주소 눌렀을 때 도로명, 지번 나오는 div
                        addr = browser.find_elements(By.CSS_SELECTOR, '.Y31Sf > div')

                        # 지번만 있는 경우
                        if len(addr) == 1 and addr.__getitem__(0).text[0:2] == '지번':
                            jibun = addr.__getitem__(0).text
                            last_index = jibun.find('복사우\n')    # 복사버튼, 우편번호 제외하기 위함

                            jibun_address = jibun[2:last_index]
                            print("지번 주소:", jibun_address)

                        # 도로명만 있는 경우
                        elif len(addr) == 1 and addr.__getitem__(0).text[0:2] == '도로':
                            road = addr.__getitem__(0).text
                            last_index = road.find('복사우\n')     # 복사버튼, 우편번호 제외하기 위함

                            road_address = road[3:last_index]
                            print("도로명 주소:", road_address)

                        # 도로명, 지번 둘 다 있는 경우
                        else:
                            # 도로명
                            road = addr.__getitem__(0).text

                            road_address = road[3:(len(road) - 2)]
                            print("도로명 주소:", road_address)

                            # 지번
                            jibun = addr.__getitem__(1).text
                            last_index = jibun.find('복사우\n')    # 복사버튼, 우편번호 제외하기 위함

                            jibun_address = jibun[2:last_index]
                            print("지번 주소:", jibun_address)

                    except Exception as e:
                        print(e)
                        print('[주소 수집 Error...]')

                    ''' (4) 메뉴 가지고 오기 '''
                    #메뉴 추출하기 
                    menu_list = browser.find_elements(By.CSS_SELECTOR,"div.flicking-camera>a")
        
                    #nav에서 길이가 4 이하면 메뉴가 2번째에 위치, 4초과면 3번째에 위치한다. 
                    ismenu = False
                    for i in range(len(menu_list)): 
                        if menu_list.__getitem__(i).text == '메뉴':
                            menu_btn = menu_list.__getitem__(i)
                            menu_btn.click()
                            ismenu = True
                            # time.sleep(0.5)
                            break
                            

                    #메뉴 페이지 더보기 처리
                    time.sleep(2) 
                    page_down(30)  

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
                                        time.sleep(0.4)
                                    time.sleep(1)

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
                                print("메뉴 이름 : ",menu_name)
                                menu_price = menul[idx].find_element(By.CSS_SELECTOR, ".price").text
                                print("메뉴 가격 : ",menu_price)
                            else :
                                menu_name = menul[idx].find_element(By.CSS_SELECTOR, ".yQlqY > span.lPzHi").text
                                print("메뉴 이름 : ",menu_name)
                                menu_price = menul[idx].find_element(By.CSS_SELECTOR, ".GXS1X").text
                                print("메뉴 가격 : ",menu_price)
                    
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
                   'food_menu' : food_menu
                    }
                    food_dict.append(dict_temp)
                    print(f'{food_name} ...완료')

                    time.sleep(1)
                    

                except Exception as e:
                    print(e)
                    print('ERROR!' * 3)

                    # dict에 데이터 집어넣기
                    food_menu = ', '.join([f'{menu}: {price}' for menu, price in food_menu.items()])   
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
                   'food_menu' : food_menu
                    }
                    food_dict.append(dict_temp)

                    print(f'[{food_name} ...완료]')
                    time.sleep(1)


        # 프레임 다시 전환
        switch_frame('searchIframe')    

        # 다음 페이지 버튼 누를 수 없으면 종료
        l = len(next_btn_list)
        element = browser.find_element(By.CSS_SELECTOR, f'div.zRM9F> a:nth-of-type({l})')
        aria_disabled_value = element.get_attribute("aria-disabled")

        if aria_disabled_value == "true":
            print("[다음 버튼 클릭❌...]")
            break


        if names[-1]:  # 마지막 주차장일 경우 다음버튼 클릭
            element.click()
            print("[다음 버튼 Click...]")
            time.sleep(2)

        else:
            print('[페이지 인식❌ ...]')
            break

    print(f'[{key_word} 관련 음식점 검색 ...완료]')

    # TXT 파일에 index 번호 입력
    with open('index_numbers.txt', 'w', encoding='utf-8') as txt_file:
        txt_file.write(f'{index}\n')

print('[데이터 수집 완료]\n소요 시간 :', time.time() - start)
browser.quit()  # 작업이 끝나면 창을 닫는다.

result_df = pd.DataFrame(food_dict)
result_df.to_csv(output_csv_file, index=False, encoding='utf-8-sig')
result_df_v1 = pd.DataFrame(food_menu_dict)
result_df_v1.to_csv(output_csv_file_v1, index=False, encoding='utf-8-sig')
