const insertStudent = async() => {
    $('#saveFunction').val('insert');
    $('#studentForm')[0].reset();
    $('#userId').prop('readonly', false);
}

const updateStudent = async(e) => {
    $('#saveFunction').val('update');
    $('#stickerPreview').css('display', 'none');
    var userId = e.getAttribute('user-id');    
    const res = await fetch(`http://localhost:8080/student/findStudent/${userId}`);
    const {state, message, data} = await res.json();

    $('#userId').val(data[0].userId);
    $('#classId').val(data[0].classId);
    $('#userName').val(data[0].userName);
    $('#depId').val(data[0].departmentId);
    $('input[name="sex"][value="' + data[0].sex + '"]').prop('checked', true);
    $('#birthDate').val(data[0].birthDate);
    $('#email').val(data[0].email);
    $('#phone').val(data[0].phone);
    $('#admissionDate').val(data[0].admissionDate);
    $('#classGrade').val(data[0].grade);
    $('#className').val(data[0].className);
    $('#userId').prop('readonly', true);
    
    if (data[0].sticker) {
        // 使用正則表達式提取文件名
        var parts = data[0].sticker.split('/');
        const fileName = parts[parts.length-1];

        $('#stickerPreview').attr("src", `img/${fileName}`);
        $('#stickerPreview').css('display', 'inline');
    }
}

const saveStudent = async()=> {

    var formData = new FormData($('#studentForm')[0]);

    var saveFunction = $('#saveFunction').val();
    var flag = saveFunction == 'insert';
    var method = flag ? 'POST' : 'PUT';
    var userId = $('#userId').val();
    var url = flag ? 'http://localhost:8080/student/insertStudent' : `http://localhost:8080/student/updateStudent/${userId}`;

    const response = await fetch(url, {
        method : method,
        body : formData
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

const deleteStudent = async(e) => {
    if (confirm('是否要刪除')) {
        var userId = e.getAttribute('user-id');

        const response = await fetch(`http://localhost:8080/student/deleteStudent/${userId}`, {
            method : "DELETE"
        });
        const { state, message, data } = await response.json();
        alert(message);
        if (state) {
            searchStudent();
        }
    }
}

const searchStudent = async() => {
    $('#studentBody').html('');
    const response = await fetch('http://localhost:8080/student/findStudents');
    const {state, message, data} = await response.json();
    if (state) {
        data.forEach(student => {
            $('#studentBody').append(`
                <tr>
                    <td>${student.userId}</td>
                    <td>${student.userName}</td>
                    <td>${student.departmentName}</td>
                    <td>${student.gender}</td>
                    <td>${student.birthDate}</td>
                    <td>${student.email}</td>
                    <td>${student.phone}</td>
                    <td>${student.grade}</td>
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