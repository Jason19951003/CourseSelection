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
        const response = await fetch(`http://localhost:8080/course/findTeacher/${courseDep}`);
        const {state, message, data} = await response.json();
        data.forEach(obj => {
            $('#teacherId').append(`<option value="${obj.userId}">${obj.userName}</option>`)
        });
    });

    $('#courseDep').change();
}

const renderHtml = async(id, url) => {    
    const response = await fetch(`http://localhost:8080/${url}`);
    const html = await response.text();
    $(`#${id}`).html(html);
}