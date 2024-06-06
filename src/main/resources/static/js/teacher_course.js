const findCourseByTeacherId = async() => {
    $('#courseBody').html('');
    var formData = {
        teacherId: localStorage.getItem('userId'),
        courseYear : $('#year').val()
    }
    var queryString = new URLSearchParams(formData).toString();

    const response = await fetch(`http://localhost:8080/course/findCourse?${queryString}`, {
        headers : {
            "Authorization": `Bearer ${token}`
        }
    });
    const { state, message, data } = await response.json();
    if (state) {
        data.forEach(obj => {
            $('#courseBody').append(`
            <tr>
                <td>${obj.courseYear}</td>
                <td>${obj.semester}</td>
                <td>${obj.departmentName}</td>
                <td>${obj.courseName}</td>
                <td>${obj.courseContent}</td>
                <td>
                    <button id="updateCourse" onclick="updateCourse(this)" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#courseModal" course-index="${obj.courseIndex}" course-dep="${obj.courseDep}" course-name="${obj.courseName}" course-content=${obj.courseContent} course-year="${obj.courseYear}" course-semester="${obj.courseSemester}">編輯</button>
                </td>
            </tr>
            `);
        });
    }
}

const updateCourse = (e) => {
    $('#depId').val(e.getAttribute('course-dep'));
    $('#courseName').val(e.getAttribute('course-name'));
    $('#courseYear').val(e.getAttribute('course-year'));
    $('#courseSemester').val(e.getAttribute('course-semester'));
    $('#courseIndex').val(e.getAttribute('course-index'));
    $('#courseContent').val(e.getAttribute('course-content'));
}


const saveCourse = async () => {
    var formData = {
        courseIndex: $('#courseIndex').val(),
        courseContent: $('#courseContent').val()
    }
    
    const response = await fetch(`http://localhost:8080/course/updateCourse/${formData.courseIndex}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(formData)
    });

    const { state, message, data } = await response.json();

    if (state) {
        var modalElement = document.getElementById('courseModal');
        var modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();
        $('#courseForm')[0].reset();
        findCourseByTeacherId();
    }
    alert(message);
}