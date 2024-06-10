import random

# 假設已經從數據庫中獲取以下數據
classes = [
    {'class_id': 1, 'department_id': 'IM', 'grade': 1, 'class_name': '甲'},
    {'class_id': 2, 'department_id': 'IM', 'grade': 1, 'class_name': '乙'},
    {'class_id': 3, 'department_id': 'IM', 'grade': 1, 'class_name': '丙'},
    {'class_id': 4, 'department_id': 'IM', 'grade': 2, 'class_name': '甲'},
    {'class_id': 5, 'department_id': 'IM', 'grade': 2, 'class_name': '乙'},
    {'class_id': 6, 'department_id': 'IM', 'grade': 2, 'class_name': '丙'},
    {'class_id': 7, 'department_id': 'IM', 'grade': 3, 'class_name': '甲'},
    {'class_id': 8, 'department_id': 'IM', 'grade': 3, 'class_name': '乙'},
    {'class_id': 9, 'department_id': 'IM', 'grade': 3, 'class_name': '丙'},
    {'class_id': 10, 'department_id': 'IM', 'grade': 4, 'class_name': '甲'},
    {'class_id': 11, 'department_id': 'IM', 'grade': 4, 'class_name': '乙'},
    {'class_id': 12, 'department_id': 'IM', 'grade': 4, 'class_name': '丙'},
]

courses = [
    {'course_index': 1, 'course_dep': 'IM', 'course_id': '001', 'grade': 1, 'course_name': '程式設計導論', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113001', 'course_content': '程式設計導論'},
    {'course_index': 2, 'course_dep': 'IM', 'course_id': '002', 'grade': 1, 'course_name': '計算機概論', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113002', 'course_content': '計算機概論'},
    {'course_index': 3, 'course_dep': 'IM', 'course_id': '003', 'grade': 1, 'course_name': '微積分', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113003', 'course_content': '微積分'},
    {'course_index': 4, 'course_dep': 'IM', 'course_id': '004', 'grade': 1, 'course_name': '英文', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113004', 'course_content': '英文'},
    {'course_index': 5, 'course_dep': 'IM', 'course_id': '005', 'grade': 1, 'course_name': '資料結構', 'course_required': '1', 'course_year': 3, 'course_semester': '2', 'teacher_id': 'TE113005', 'course_content': '資料結構'},
    {'course_index': 6, 'course_dep': 'IM', 'course_id': '006', 'grade': 1, 'course_name': '演算法', 'course_required': '1', 'course_year': 3, 'course_semester': '2', 'teacher_id': 'TE113006', 'course_content': '演算法'},
    {'course_index': 7, 'course_dep': 'IM', 'course_id': '007', 'grade': 1, 'course_name': '作業系統', 'course_required': '1', 'course_year': 3, 'course_semester': '2', 'teacher_id': 'TE113007', 'course_content': '作業系統'},
    {'course_index': 8, 'course_dep': 'IM', 'course_id': '008', 'grade': 1, 'course_name': '數位邏輯設計', 'course_required': '1', 'course_year': 3, 'course_semester': '2', 'teacher_id': 'TE113008', 'course_content': '數位邏輯設計'},
    {'course_index': 9, 'course_dep': 'IM', 'course_id': '009', 'grade': 2, 'course_name': '資料庫系統', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113009', 'course_content': '資料庫系統'},
    {'course_index': 10, 'course_dep': 'IM', 'course_id': '010', 'grade': 2, 'course_name': '軟體工程', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113010', 'course_content': '軟體工程'},
    {'course_index': 11, 'course_dep': 'IM', 'course_id': '011', 'grade': 2, 'course_name': '計算機網路', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113011', 'course_content': '計算機網路'},
    {'course_index': 12, 'course_dep': 'IM', 'course_id': '012', 'grade': 2, 'course_name': '人工智慧', 'course_required': '1', 'course_year': 3, 'course_semester': '2', 'teacher_id': 'TE113012', 'course_content': '人工智慧'},
    {'course_index': 13, 'course_dep': 'IM', 'course_id': '013', 'grade': 2, 'course_name': '資訊安全', 'course_required': '1', 'course_year': 3, 'course_semester': '2', 'teacher_id': 'TE113001', 'course_content': '資訊安全'},
    {'course_index': 14, 'course_dep': 'IM', 'course_id': '014', 'grade': 3, 'course_name': '數據分析', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113002', 'course_content': '數據分析'},
    {'course_index': 15, 'course_dep': 'IM', 'course_id': '015', 'grade': 3, 'course_name': '專題製作', 'course_required': '1', 'course_year': 3, 'course_semester': '1', 'teacher_id': 'TE113003', 'course_content': '專題製作'},
    {'course_index': 16, 'course_dep': 'IM', 'course_id': '016', 'grade': 3, 'course_name': '專題討論', 'course_required': '1', 'course_year': 3, 'course_semester': '2', 'teacher_id': 'TE113004', 'course_content': '專題討論'},
]

# 定義上課的星期和時間段
weekdays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']
time_slots = [(1, 2), (3, 4), (5, 6), (7, 8)]
# 設定可用的教室位置
locations = ['A101', 'A102', 'A103', 'A104',
             'A201', 'A202', 'A203', 'A204',
             'A301', 'A302', 'A303', 'A304',
             'A401', 'A402', 'A403', 'A404']

# 存儲已分配的時間和地點，防止重複
assigned_slots = {}

def generate_course_offerings(year):
    sql_statements = []

    for course in courses:
        for class_info in classes:
            if course['grade'] == class_info['grade']:
                assigned = False
                while not assigned:
                    day = random.choice(weekdays)
                    start, end = random.choice(time_slots)
                    location = random.choice(locations)

                    # 檢查時間和地點是否已被占用
                    if (day, start, end, location) not in assigned_slots:
                        assigned_slots[(day, start, end, location)] = True
                        assigned = True

                        sql = f"INSERT INTO course_offerings (course_index, course_year, course_semester, course_class_id, course_of_week, course_start, course_end, course_locate, teacher_id, course_content) " \
                              f"VALUES ({course['course_index']}, {year}, '{course['course_semester']}', {class_info['class_id']}, '{day}', {start}, {end}, '{location}', '{course['teacher_id']}', '{course['course_content']}');"
                        sql_statements.append(sql)
                        break

    return sql_statements

# 假設要排課的年分是 2023 年 (民國 112 年)
year = 112
sql_statements = generate_course_offerings(year)

# 輸出 SQL 語句
for sql in sql_statements:
    print(sql)
