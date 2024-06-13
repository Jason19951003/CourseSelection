const saveCourse = async () => {
    var formData = new FormData($('#courseForm')[0]);
    var jsonData = {
        
    }

    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    var saveFunction = $('#saveFunction').val();
    var uri = saveFunction == 'insert' ? 'insertCourseInfo' : `updateCourseInfo/${formData.courseIndex}`;
    var method = saveFunction == 'insert' ? 'POST' : 'PUT';
    
    const response = await fetch(`${ip}/course/${uri}`, {
        method: `${method}`,
        headers: {
            'Content-Type'  : 'application/json',
            'Authorization' : `Bearer ${token}`
        },
        body: JSON.stringify(jsonData)
    });

    const { state, message, data } = await response.json();

    if (state) {
        var modalElement = document.getElementById('courseModal');
        var modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();
        $('#courseForm')[0].reset();
        searchCourse();
    }
    alert(message);
}

const insertCourseInfo = async () => {
    $('#courseForm')[0].reset();
    $('#saveFunction').val('insert');
}

const deleteCourseInfo = async (e) => {
    if (confirm("是否要刪除?")) {
        var courseIndex = e.getAttribute('course-index');
        const response = await fetch(`${ip}/course/deleteCourseInfo/${courseIndex}`, {
            method: "DELETE",
            headers : {
                'Authorization': `Bearer ${token}`
            }
        });
        const { state, message, data } = await response.json();
        alert(message);
        if (state) {
            searchCourse();
        }
    }
}

const updateCourseInfo = async (e) => {
    var course = JSON.parse($(e).attr('data-course'));
    
    $('#depId').val(course.courseDep);
    $('#courseIndex').val(course.courseIndex);
    $('#courseId').val(course.courseId);
    $('#courseGrade').val(course.courseGrade);
    $('#courseName').val(course.courseName);
    $('#courseRequired').val(course.courseRequired);
    $('#courseCredit').val(course.courseCredit);
    $('#teacherId').val(course.teacherId);
    $('#saveFunction').val('update');
}

const searchCourse = async () => {
    $('#courseBody').html('');
    var param = new URLSearchParams({depId : $('#user-header select[name="depId"').val()}).toString();
    const response = await fetch(`${ip}/course/findCourseInfo?${param}`, {
        headers : {
            'Authorization': `Bearer ${token}`
        }
    });
    
    const { state, message, data } = await response.json();
    if (state) {
        data.forEach(obj => {
            var objStr = JSON.stringify(obj);
            $('#courseBody').append(`
            <tr>
                <td>${obj.departmentName}</td>
                <td>${obj.courseGrade}</td>
                <td>${obj.courseId}</td>
                <td>${obj.courseName}</td>
                <td>${obj.required}</td>
                <td>${obj.courseCredit}</td>
                <td>${obj.userName}</td>
                <td>
                    <button id="updateCourseInfo" onclick="updateCourseInfo(this)" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#courseModal" data-course='${objStr}'>編輯</button>
                    <button id="deleteCourseInfo" onclick="deleteCourseInfo(this)" class="btn btn-danger btn-sm" course-index="${obj.courseIndex}">刪除</button>
                </td>
            </tr>
            `);
        });
    }
}