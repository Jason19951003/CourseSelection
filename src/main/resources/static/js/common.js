const token = localStorage.getItem('token'); // 從 localStorage 獲取 token
const loadDepartment = async() => {
    const response = await fetch('http://localhost:8080/course/findDepartment',{
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
                'Content-type' : 'application/json',
                "Authorization": `Bearer ${token}`
            }
        });
        
        var {state, message, data} = await res.json();
        if (data[0]) {
            $('#classId').val(data[0].classId);
        }
        
    });
    
    $('#depId').change();
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
    window.location.href = "/index.html"
}