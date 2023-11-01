import pandas as pd

pd.set_option('display.max_columns',None)
csv_file_path = './food/web-scraping/fulldata_07_24_04_P_일반음식점.csv'
output_csv_file = './food/web-scraping/FoodSearchData.csv'

# CSV 파일을 pandas로 읽기
df = pd.read_csv(csv_file_path, encoding='cp949')
df = df.loc[df['영업상태명'] != '폐업']
df = df[['번호','개방서비스명','소재지전체주소','도로명전체주소','사업장명','업태구분명','좌표정보(x)','좌표정보(y)']]

tmp = df['소재지전체주소'].str.split(" ")
df['지역'] = tmp.str.get(2)
df['사업장명'] = df['사업장명'].str.rstrip('0123456789') #사업장명 마지막글자에 숫자가 있으면 제거 
df = df.drop_duplicates(subset=['사업장명'], keep='first') #중복사업자명 제거 
# print(df.head)

df.to_csv(output_csv_file, index=False, encoding='cp949')