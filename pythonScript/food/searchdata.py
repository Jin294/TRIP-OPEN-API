import pandas as pd

csv_file_path = 'searchData_ori.csv'
output_csv_file = 'searchData_new.csv'

# CSV 파일을 pandas로 읽기
df = pd.read_csv(csv_file_path, encoding='cp949')

# 데이터 정제하기
df['title'] = df['title'].str.replace(r'\([^)]*\)|\[[^\]]*\]', '', regex=True)
df['naddr1'] = df['addr1'].str.split(' ').str[:2].str.join(' ')
df['naddr2'] = df['addr1'].str.split(' ').str[:3].str.join(' ')

df.to_csv(output_csv_file, index=False, encoding='cp949')