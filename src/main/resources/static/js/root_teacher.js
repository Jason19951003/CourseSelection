const insertTeacher = async() => {
    $('#saveFunction').val('insert');
    $('#teacherForm')[0].reset();
    $('#userId').prop('readonly', false);
}

const updateTeacher = async(e) => {
    $('#saveFunction').val('update');
    $('#stickerPreview').css('display', 'none');
    var userId = e.getAttribute('user-id');    
    const res = await fetch(`http://localhost:8080/teacher/findTeacher/${userId}`);
    const {state, message, data} = await res.json();

    $('#userId').val(data[0].userId);
    $('#classId').val(data[0].classId);
    $('#userName').val(data[0].userName);
    $('#courseDep').val(data[0].departmentId);
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

const saveTeacher = async()=> {

    var formData = new FormData($('#teacherForm')[0]);

    var saveFunction = $('#saveFunction').val();
    var flag = saveFunction == 'insert';
    var method = flag ? 'POST' : 'PUT';
    var userId = $('#userId').val();
    var url = flag ? 'http://localhost:8080/teacher/insertTeacher' : `http://localhost:8080/teacher/updateTeacher/${userId}`;

    const response = await fetch(url, {
        method : method,
        body : formData
    });
    
    const {state, message, data} = await response.json();
    if (state) {
        // 關閉modal
        var modalElement = document.getElementById('teacherModal');
        var modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();
        $('#teacherForm')[0].reset();
        searchTeacher();
    }
    alert(message);
}

const deleteTeacher = async(e) => {
    if (confirm('是否要刪除')) {
        var userId = e.getAttribute('user-id');

        const response = await fetch(`http://localhost:8080/teacher/deleteTeacher/${userId}`, {
            method : "DELETE"
        });
        const { state, message, data } = await response.json();
        alert(message);
        if (state) {
            searchTeacher();
        }
    }
}

const searchTeacher = async() => {
    $('#teacherBody').html('');
    const response = await fetch('http://localhost:8080/teacher/findTeachers')
    const {state, message, data} = await response.json();
    if (state) {
        data.forEach(teacher => {
            $('#teacherBody').append(`
                <tr>
                    <td>${teacher.userId}</td>
                    <td>${teacher.userName}</td>
                    <td>${teacher.departmentName}</td>
                    <td>${teacher.gender}</td>
                    <td>${teacher.birthDate}</td>
                    <td>${teacher.email}</td>
                    <td>${teacher.phone}</td>
                    <td>${teacher.grade}</td>
                    <td>${teacher.className}</td>
                    <td>
                        <button id="updateCourse" onclick="updateTeacher(this)" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#teacherModal" user-id="${teacher.userId}">編輯</button>
                        <button id="deleteCourse" onclick="deleteTeacher(this)" class="btn btn-danger btn-sm" user-id="${teacher.userId}">刪除</button>
                    </td>
                </tr>
            `)
        });
    }
}