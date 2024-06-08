const token = localStorage.getItem('token');
const userName = localStorage.getItem('userName');
const userId = localStorage.getItem('userId');

const loadDepartment = async() => {
    const response = await fetch('http://localhost:8080/course/findDepartment', {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });
    const {state, message, data} = await response.json();
    
    data.forEach(obj => {
        if (obj.departmentId == 'IM') {
            $('#depId').append(`<option value="${obj.departmentId}" selected>${obj.departmentName}</>`);
        } else {
            $('#depId').append(`<option value="${obj.departmentId}">${obj.departmentName}</>`);
        }
    });
    
    $('#depId').on('change', async function() {
        // 要先清空Select裡的html才能append(不然會接續在後面)
        $('#teacherId').html('');
        const depId = $('#depId').val();
        const res = await fetch(`http://localhost:8080/course/findTeacher/${depId}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });
        var {state, message, data} = await res.json();
        data.forEach(obj => {
            $('#teacherId').append(`<option value="${obj.userId}">${obj.userName}</option>`);
        });
        
        $('#classGrade').change();
    });
    // 同時給多個element 註冊同一個事件
    $('#classGrade, #className').on('change', async(e)=> {
        // 獲取參數值
        const depId = $('#depId').val();
        const classGrade = $('#classGrade').val();
        const className = $('#className').val();

        // 構建查詢字符串
        const queryString = new URLSearchParams({
            depId: depId,
            classGrade: classGrade,
            className: className
        }).toString();

        const res = await fetch(`http://localhost:8080/user/findClassInfo?${queryString}`, {
            method : 'GET',
            headers : {
                "Authorization": `Bearer ${token}`
            }
        });
        
        var {state, message, data} = await res.json();
        if (data[0]) {
            $('#classId').val(data[0].classId);
        }
        loadCourseGrade();
    });
    
    $('#depId').change();
}

const loadCourseGrade = async() => {
    const params = {
        'teacherId': `${userId}`,
        'courseId' : $('#courseId').val(),
        'depId' : $('#depId').val(),
        'classId' : $("#classId").val()
    };
    var queryString = new URLSearchParams(params);
    $('#gradeBody').html('');
    const response = await fetch(`http://localhost:8080/course/findGrade?${queryString}`, {
        headers : {
            'Authorization': `Bearer ${token}`,
        }
    });
    const {state, message, data} = await response.json();
    
    data.forEach(obj => {
        $('#gradeBody').append(`
            <tr>
                <td>${obj.courseYear}</td>
                <td>${obj.courseSemester}</td>
                <td>${obj.courseDep}</td>
                <td>${obj.courseName}</td>
                <td>${obj.userName}</td>
                <td>${obj.grade}</td>
            </tr>
        `);
    });
}

const loadTeacherCourse = async() => {
    $('#courseId').html('');
    const response = await fetch(`http://localhost:8080/course/findTeacherCourseById/${userId}`, {
        headers : {
            "Authorization": `Bearer ${token}`
        }
    });
    const {state, message, data} = await response.json();
    data.forEach(obj => {
        $('#courseId').append(`<option value="${obj.courseId}">${obj.courseName}</option>`);
    });
}

const renderHtml = async(id, url) => {
    const response = await fetch(`http://localhost:8080/${url}`, {
        headers : {
            "Authorization": `Bearer ${token}`
        }
    });
    const html = await response.text();
    $(`#${id}`).html(html);
}

const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    window.location.href = "/index.html"
}