import openpyxl
import random
import string
from datetime import datetime, timedelta
from faker import Faker

# 初始化 Faker
fake = Faker('zh_TW')

# 建立一個新的 Excel 工作簿
wb = openpyxl.Workbook()
ws = wb.active
ws.title = "學生資料"

# 添加標頭
headers = ["學號", "姓名", "性別", "生日", "Email", "手機", "科系", "班級", "入學日期"]
ws.append(headers)

# 生成學生資料
def random_date(start, end):
    return start + timedelta(days=random.randint(0, (end - start).days))

start_date = datetime(2004, 9, 1)
end_date = datetime(2005, 8, 31)

for i in range(1, 151):
    student_id = f"IM112{str(i).zfill(3)}"
    name = fake.name()
    gender = random.choice([1, 2])
    birthdate = random_date(start_date, end_date).strftime("%Y-%m-%d")
    email = f"{student_id}@example.com"
    phone = f"09{''.join(random.choices(string.digits, k=8))}"
    department = "IM"
    
    if i <= 50:
        class_name = "甲"
    elif i <= 100:
        class_name = "乙"
    else:
        class_name = "丙"
        
    entry_date = "2023-09-01"
    
    ws.append([student_id, name, gender, birthdate, email, phone, department, class_name, entry_date])

# 保存文件
wb.save("user_info.xlsx")
