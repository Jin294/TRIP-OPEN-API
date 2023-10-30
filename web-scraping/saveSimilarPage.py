from datasets import load_dataset
from pymongo import MongoClient
from koreanCheck import remove_non_korean
import pymysql
from MongoDBConnector import MongoDBConnector
import re

# mongo db 연결
connector = MongoDBConnector("wiki_HTML")

# mysql 연결 후 관광지 정보 저장
mysqlConnector = pymysql.connect(host='k9b205.p.ssafy.io', user='b205', password='9gi_ssafy_final', db='b205', charset='utf8')
cur = mysqlConnector.cursor()
sql = 'select title from attraction_info'
result = cur.execute(sql)
attractionArr = cur.fetchall()

# 현재 진행이 몇개까지 됐는지 count 변수
cnt = 0

# 모든 관광지에 대해 조회
for title in attractionArr:
    print(title)
    #관광지 매핑 진행 얼마나 됐는지 페이지에 저장
    with open('attraction_index_numbers.txt', 'w', encoding='utf-8') as txt_file:
        txt_file.write(f'{cnt}\n')
        cnt+=1

    if cnt == 3: break
    #관광지 이름 추출
    stringArr = re.split(r'\s+|\(', title[0])
    
    newArr = []
    for string in stringArr:
        newArr.append(remove_non_korean(string))

    for string in newArr:
        stringLen = len(string)
        for i in range(2, stringLen - 1):
            firstStr = string[0:i]
            secondStr = string[i:]
        


    # flag = False
    # for string in stringArr:
    #     print(string)
      

    attraction_title = remove_non_korean(title[0])
    


    

    


# print(dataset["train"][1000]["title"])
# print(dataset["train"][1000]["text"])