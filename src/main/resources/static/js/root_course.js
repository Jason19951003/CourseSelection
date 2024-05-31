const saveCourse = async () => {
    var formData = {
        courseIndex: $('#courseIndex').val(),
        depId: $('#depId').val(),
        courseId: $('#courseId').val(),
        courseName: $('#courseName').val(),
        courseRequired: $('#courseRequired').val(),
        teacherId: $('#teacherId').val(),
        courseYear: $('#courseYear').val(),
        courseSemester: $('#courseSemester').val(),
        courseOfWeek: $('#courseOfWeek').val(),
        courseStart: $('#courseStart').val(),
        courseEnd: $('#courseEnd').val(),
        courseLocate: $('#courseLocate').val()
    }

    var saveFunction = $('#saveFunction').val();
    var uri = saveFunction == 'insert' ? 'insertCourse' : `updateCourse/${formData.courseIndex}`;
    var method = saveFunction == 'insert' ? 'POST' : 'PUT';

    const response = await fetch(`http://localhost:8080/course/${uri}`, {
        method: `${method}`,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
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

const insertCourse = async () => {
    $('#courseForm')[0].reset();
    $('#saveFunction').val('insert');
}

const deleteCourse = async (e) => {
    if (confirm("是否要刪除?")) {
        var courseIndex = e.getAttribute('course-index');
        const response = await fetch(`http://localhost:8080/course/deleteCourse/${courseIndex}`, {
            method: "DELETE"
        });
        const { state, message, data } = await response.json();
        alert(message);
        if (state) {
            searchCourse();
        }
    }
}

const updateCourse = async (e) => {
    var formData = {
        courseIndex: e.getAttribute('course-index'),
    }
    var queryString = new URLSearchParams(formData).toString();

    const response = await fetch(`http://localhost:8080/course/findCourse?${queryString}`);
    const { state, message, data } = await response.json();
    
    $('#depId').val(data[0].courseDep);
    $('#courseIndex').val(data[0].courseIndex);
    $('#courseId').val(data[0].courseId);
    $('#courseName').val(data[0].courseName);
    $('#courseRequired').val(data[0].courseRequired);
    $('#teacherId').val(data[0].teacherId);
    $('#courseYear').val(data[0].courseYear);
    $('#courseSemester').val(data[0].courseSemester);
    $('#courseOfWeek').val(data[0].courseOfWeek);
    $('#courseStart').val(data[0].courseStart);
    $('#courseEnd').val(data[0].courseEnd);
    $('#courseLocate').val(data[0].courseLocate);
    $('#saveFunction').val('update');
}

const searchCourse = async () => {
    $('#courseBody').html('');
    const response = await fetch('http://localhost:8080/course/findCourse');
    const { state, message, data } = await response.json();
    if (state) {
        data.forEach(obj => {
            $('#courseBody').append(`
            <tr>
                <td>${obj.departmentName}</td>
                <td>${obj.courseName}</td>
                <td>${obj.required}</td>
                <td>${obj.teacherName}</td>
                <td>${obj.courseYear}</td>
                <td>${obj.semester}</td>
                <td>${obj.courseTime}</td>
                <td>${obj.courseLocate}</td>
                <td>
                    <button id="updateCourse" onclick="updateCourse(this)" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#courseModal" course-index="${obj.courseIndex}">編輯</button>
                    <button id="deleteCourse" onclick="deleteCourse(this)" class="btn btn-danger btn-sm" course-index="${obj.courseIndex}">刪除</button>
                </td>
            </tr>
            `);
        });
    }
}