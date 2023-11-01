from MongoDBConnector import MongoDBConnector
from datasets import load_dataset

# 나무위키 데이터셋 추출
dataset = load_dataset("heegyu/namuwiki")

# mongo db 연결
connector = MongoDBConnector("namu_page")


# 나무위키 데이터셋에서 제목과 내용 추출
for namu in dataset["train"]:
    title = namu["title"]
    content = namu["text"]
   
    connector.insert_data({
        "title":title,
        "content":content
    })