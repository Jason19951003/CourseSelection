const findCourseByTeacherId = async() => {
    $('#courseBody').html('');
    var formData = {
        teacherId: sessionStorage.getItem('userId'),
        courseYear : $('#year').val(),
        courseSemester : $('#year').find('option:selected').data('semester')
    }
    var queryString = new URLSearchParams(formData).toString();

    const response = await fetch(`${ip}/course/findCourse?${queryString}`, {
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
    // 將課程資訊的html格式顯示到編輯器上
    CKEDITOR.instances.courseContent.setData(courseData.courseContent);
}

const saveCourse = async() => {
    var formData = {
        courseIndex: $('#courseIndex').val(),
        // 取得編輯器的html存到資料庫
        courseContent: CKEDITOR.instances.courseContent.getData(),
        courseYear : $('#courseYear').val(),
        courseSemester : $('#courseSemester').val(),
        classId : $('#classId').val(),
        teacherId : `${userId}`
    }
    
    const response = await fetch(`${ip}/course/updateCourseOfferings/${formData.courseIndex}`, {
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
    Swal.fire({
        title: message,
        icon: state ? "success" : "error"
    });
}