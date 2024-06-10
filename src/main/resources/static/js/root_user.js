const insertUser = async(form, permissionId) => {
    $('#permissionId').val(permissionId);
    $('#saveFunction').val('insert');
    $(`#${form}`)[0].reset();
    $('#userId').prop('readonly', false);
    $('#avatarPreview').css('display', 'none');
}

const updateUser = async(e, permissionId) => {
    $('#permissionId').val(permissionId);
    $('#saveFunction').val('update');
    $('#avatarPreview').css('display', 'none');
    var userId = e.getAttribute('user-id');
    const queryString = new URLSearchParams({
            userId : userId,
            permissionId : permissionId
    }).toString();
    const res = await fetch(`http://localhost:8080/user/findUser?${queryString}`, {
        headers : {
            "Authorization": `Bearer ${token}`
        }
    });
    const {state, message, data} = await res.json();

    $('#userId').val(data[0].userId);
    $('#classId').val(data[0].classId);
    $('#userName').val(data[0].userName);
    $('#depId').val(data[0].departmentId).change();
    $('input[name="sex"][value="' + data[0].sex + '"]').prop('checked', true);
    $('#birthDate').val(data[0].birthDate);
    $('#email').val(data[0].email);
    $('#phone').val(data[0].phone);
    $('#admissionDate').val(data[0].admissionDate);
    $('#classGrade').val(data[0].grade);
    $('#className').val(data[0].className);
    $('#userId').prop('readonly', true);
    
    if (data[0].avatar) {
        // 取得檔名
        var parts = data[0].avatar.split('/');
        const fileName = parts[parts.length-1];

        $('#avatarPreview').attr("src", `img/${fileName}`);
        $('#avatarPreview').css('display', 'inline');
    }
}

const saveUser = async(form, body, modal) => {
    try {
        var formData = new FormData($(`#${form}`)[0]);

        var saveFunction = $('#saveFunction').val();
        var flag = saveFunction == 'insert';
        var method = flag ? 'POST' : 'PUT';
        var userId = $('#userId').val();
        var url = flag ? 'http://localhost:8080/user/insertUser' : `http://localhost:8080/user/updateUser/${userId}`;
    
        const response = await fetch(url, {
            method : method,
            headers : {
                "Authorization": `Bearer ${token}`
            },
            body : formData
        });
        
        const {state, message, data} = await response.json();
        if (state) {
            // 關閉modal
            var modalElement = document.getElementById(`${modal}`);
            var modalInstance = bootstrap.Modal.getInstance(modalElement);
            modalInstance.hide();
            $(`#${form}`)[0].reset();
            searchUser(body, modal, $('#permissionId').val());
        }
        alert(message);
    } catch (error) {
        console.log(error);
    }
}

const deleteUser = async(e, body, modal, permissionId) => {
    if (confirm('是否要刪除')) {
        try {
            var userId = e.getAttribute('user-id');

            const response = await fetch(`http://localhost:8080/user/deleteUser/${userId}`, {
                method : "DELETE",
                headers : {
                    "Authorization": `Bearer ${token}`
                }
            });
            if (!response.ok) {
                console.log(response);
            }
            const { state, message, data } = await response.json();
            alert(message);
            if (state) {
                searchUser(body, modal, permissionId);
            }
        } catch (error) {
            console.log(error);
        }
    }
}

const searchUser = async(body, modal, permissionId) => {
    $(`#${body}`).html('');
    var classId = $('#user-header input[name="classId"]').val() ? $('#user-header input[name="classId"]').val() : ''
    console.log(classId);
    var param = {
        permissionId : permissionId,
        depId : $('#user-header select[name="depId"]').val(),
        classId : classId
    }
    const queryString = new URLSearchParams(param).toString();
    const response = await fetch(`http://localhost:8080/user/findUsers?${queryString}`, {
        headers : {
            "Authorization": `Bearer ${token}`
        }
    });
    const {state, message, data} = await response.json();
    
    if (state) {
        data.forEach(user => {
            $(`#${body}`).append(`
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>${user.departmentName}</td>
                    <td>${user.gender}</td>
                    <td>${user.birthDate}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>${user.grade}</td>
                    <td>${user.className}</td>
                    <td>
                        <button id="updateUser" onclick="updateUser(this, ${permissionId})" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#${modal}" user-id="${user.userId}">編輯</button>
                        <button id="deleteUser" onclick="deleteUser(this, '${body}', '${modal}', ${permissionId})" class="btn btn-danger btn-sm" user-id="${user.userId}">刪除</button>
                    </td>
                </tr>
            `)
        });
    }
}