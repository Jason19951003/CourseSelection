const loadDepartment = async() => {
    const response = await fetch('http://localhost:8080/course/findDepartment');
    const {state, message, data} = await response.json();
    
    data.forEach(obj => {
        $('#courseDep').append(`<option value="${obj.departmentId}">${obj.departmentName}</>`);
    });

    $('#courseDep').on('change', async() => {
        // 要先清空Select裡的html才能append(不然會接續在後面)
        $('#teacherId').html('');
        const courseDep = $('#courseDep').val();
        const teacherRes = await fetch(`http://localhost:8080/course/findTeacher/${courseDep}`);
        var {state, message, data} = await teacherRes.json();
        data.forEach(obj => {
            $('#teacherId').append(`<option value="${obj.userId}">${obj.userName}</option>`)
        });

        const classRes = await fetch(`http://localhost:8080/student/findClassInfo/${courseDep}`);
        var {state, message, data} = await classRes.json();
        $('#classGrade').html('');
        $('#className').html('');
        data.forEach(obj => {
            $('#classGrade').append(`<option value=${obj.grade}>${obj.grade}</option>`);
            $('#className').append(`<option value=${obj.className}>${obj.className}</option>`);
        });
    });

    $('#courseDep').change();
}

const renderHtml = async(id, url) => {    
    const response = await fetch(`http://localhost:8080/${url}`);
    const html = await response.text();
    $(`#${id}`).html(html);
}