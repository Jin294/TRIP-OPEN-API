from datasets import load_dataset
from pymongo import MongoClient
from koreanCheck import remove_non_korean
from text_similarity import textSimilarity
import pymysql
from MongoDBConnector import MongoDBConnector

dataset = load_dataset("heegyu/namuwiki")

connector = MongoDBConnector()

mysqlConnector = pymysql.connect(host='k9b205.p.ssafy.io', user='b205', password='9gi_ssafy_final', db='b205', charset='utf8')
cur = mysqlConnector.cursor()
sql = 'select title from attraction_info'
result = cur.execute(sql)
attractionArr = cur.fetchall()

cnt = 0
for title in attractionArr:
    attraction_title = remove_non_korean(title[0])
    print(attraction_title)
    with open('attraction_index_numbers.txt', 'w', encoding='utf-8') as txt_file:
        txt_file.write(f'{cnt}\n')
        cnt+=1

    for namu in dataset["train"]:
        title = namu["title"]
        content = namu["text"]
        if textSimilarity(title, attraction_title) < 0.55:
            continue
        connector.insert_data({
            "attraction":attraction_title, #Root 관광지 이름 저장
            "name":title, #키워드 이름 저장
            "html":content #html text 저장
        })


# print(dataset["train"][1000]["title"])
# print(dataset["train"][1000]["text"])