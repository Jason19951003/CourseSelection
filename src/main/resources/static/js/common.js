const loadDepartment = async() => {    
    const response = await fetch('http://localhost:8080/finddepartment');
    const {state, message, data} = await response.json();
    data.forEach(obj => {
        $('#depId').append(`<option value="${obj.departmentId}">${obj.departmentName}</option>`);
    });
    $('#depId').change();
}

$('#depId').on('change', async() => {
    $('#teacherId').html('');
    const depId = $('#depId').val();
    const response = await fetch(`http://localhost:8080/findteacher/${depId}`);
    const {state, message, data} = await response.json();
    data.forEach(obj => {
        $('#teacherId').append(`<option value="${obj.userId}">${obj.userName}</option>`)
    })
});