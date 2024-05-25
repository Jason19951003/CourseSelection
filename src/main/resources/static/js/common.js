const loadDepartment = async() => {
    const response = await fetch('http://localhost:8080/findDepartment');
    const {state, message, data} = await response.json();
    
    data.forEach(obj => {
        $('#courseDep').append(`<option value="${obj.departmentId}">${obj.departmentName}</option>`);
    });
    $('#courseDep').change();
}

$('#courseDep').on('change', async() => {
    // 要先清空Select裡的html才能append(不然會接續在後面)
    $('#teacherId').html('');
    const courseDep = $('#courseDep').val();
    const response = await fetch(`http://localhost:8080/findTeacher/${courseDep}`);
    const {state, message, data} = await response.json();
    data.forEach(obj => {
        $('#teacherId').append(`<option value="${obj.userId}">${obj.userName}</option>`)
    })
});