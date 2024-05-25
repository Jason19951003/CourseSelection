const loadDepartment = async() => {
    const response = await fetch('http://localhost:8080/finddepartment');
    const {state, message, data} = await response.json();
    
    data.forEach(obj => {
        $('#courseDep').append(`<option value="${obj.departmentId}">${obj.departmentName}</option>`);
    });
    $('#courseDep').change();
}

$('#courseDep').on('change', async() => {
    $('#teacherId').html('');
    const courseDep = $('#courseDep').val();
    const response = await fetch(`http://localhost:8080/findteacher/${courseDep}`);
    const {state, message, data} = await response.json();
    data.forEach(obj => {
        $('#teacherId').append(`<option value="${obj.userId}">${obj.userName}</option>`)
    })
});