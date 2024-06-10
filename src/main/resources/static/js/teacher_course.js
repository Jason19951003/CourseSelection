const findCourseByTeacherId = async() => {
    $('#courseBody').html('');
    var formData = {
        teacherId: localStorage.getItem('userId'),
        courseYear : $('#year').val(),
        courseSemester : $('#year').find('option:selected').data('semester')
    }
    var queryString = new URLSearchParams(formData).toString();

    const response = await fetch(`http://localhost:8080/course/findCourse?${queryString}`, {
        headers : {
            'Authorization': `Bearer ${token}`
        }
    });
    const { state, message, data } = await response.json();
    if (state) {
        data.forEach(obj => {
            var jsonStr = JSON.stringify(obj);
            $('#courseBody').append(`
            <tr>
                <td>${obj.courseYear}</td>
                <td>${obj.semester}</td>
                <td>${obj.departmentName}</td>
                <td>${obj.fullClassName}</td>
                <td>${obj.courseName}</td>
                <td>${obj.courseContent}</td>
                <td>
                    <button id="updateCourse" onclick="updateCourse(this)" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#courseModal" data-course='${jsonStr}'>編輯</button>
                </td>
            </tr>
            `);
        });
    }
}

const updateCourse = (e) => {
    var courseData = JSON.parse($(e).attr('data-course'));
    $('#depId').val(courseData.courseDep);
    $('#classId').val(courseData.classId);
    $('#courseName').val(courseData.courseName);
    $('#courseYear').val(courseData.courseYear);
    $('#courseSemester').val(courseData.courseSemester);
    $('#courseIndex').val(courseData.courseIndex);
    $('#courseContent').val(courseData.courseContent);
}

const saveCourse = async() => {
    var formData = {
        courseIndex: $('#courseIndex').val(),
        courseContent: $('#courseContent').val(),
        courseYear : $('#courseYear').val(),
        courseSemester : $('#courseSemester').val(),
        classId : $('#classId').val(),
        teacherId : `${userId}`
    }
    
    const response = await fetch(`http://localhost:8080/course/updateCourseOfferings/${formData.courseIndex}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
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