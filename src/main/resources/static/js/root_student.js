const saveStudent = async()=> {
    const formDataArray = $('#studentForm').serializeArray();
    const formData = {};
    formDataArray.forEach(item => {
        formData[item.name] = item.value;
    });
    const response = await fetch('http://localhost:8080/student/insertStudent', {
        method : 'POST',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(formData)
    });

    const {state, message, data} = await response.json();
    if (state) {
        // 關閉modal
        var modalElement = document.getElementById('studentModal');
        var modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();
        $('#studentForm')[0].reset();
        searchStudent();
    }
    alert(message);
}

const insertStudent = async() => {
    $('#saveFunction').val('insert');
    $('#studentForm')[0].reset();
}

const searchStudent = async() => {
    const response = await fetch('http://localhost:8080/student/findStudents')
    const {state, message, data} = await response.json();
    if (state) {
        data.forEach(student => {
            $('#studentBody').append(`
                <tr>
                    <td>${student.userId}</td>
                    <td>${student.userName}</td>
                    <td>${student.departmentName}</td>
                    <td>${student.sex}</td>
                    <td>${student.birthDate}</td>
                    <td>${student.email}</td>
                    <td>${student.phone}</td>
                    <td>${student.className}</td>
                    <td>
                        <button id="updateCourse" onclick="updateStudent(this)" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#studentModal" user-id="${student.userId}">編輯</button>
                        <button id="deleteCourse" onclick="deleteStudent(this)" class="btn btn-danger btn-sm" user-id="${student.userId}">刪除</button>
                    </td>
                </tr>
            `)
        });
    }
}