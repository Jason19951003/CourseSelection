function bindPage(id) {
    $('#page_length').on('change', async function() {
        // 1. 計算總共有幾頁
        var trLength = $(`#${id}`).find('tr').length;
        var page = Math.ceil(trLength / $(this).val());
        // 2. 實現分頁按鈕
        $('.pagination').html('');
        // 2.1 上一頁
        $('.pagination').append(`
            <li class="page-item">
                <a class="page-link" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        `);
        // 2.2 頁數
        for (var i = 0; i < page; i++) {
            $('.pagination').append(`
                <li class="page-item"><a class="page-link">${i+1}</a></li>
            `);
        }
        // 2.3 下一頁
        $('.pagination').append(`
            <li class="page-item">
                <a class="page-link" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        `);
    
        // 3. 綁定按鈕事件(要先載入完按鈕才能綁定方法)
        $('.pagination .page-link').on('click', function(e) {
            // 1. 先取得active狀態上一個與下一個按鈕
            var prev = $('.page-link.active').parent().prev();
            var next = $('.page-link.active').parent().next();
            $('.page-link.active').removeClass('active');
            // 2. 取得觸發事件的按鈕
            var pageNumber = $(this).text().trim();
            // 3. 初始化(移除上一頁和下一頁的disabled)
            $('.page-link').eq(0).removeClass('disabled');
            $('.page-link').eq($('.page-link').length-1).removeClass('disabled');
    
            // 4. 分別處理上一頁、下一頁、和數字按鈕的邏輯
            if (pageNumber === '«') {
                // 處理上一頁
                prev.children().addClass('active');
                if (prev.prev().text().trim() === '«') {
                    prev.prev().children().addClass('disabled');
                }
                pageNumber = prev.text();
                
            } else if (pageNumber === '»') {
                // 處理下一頁
                next.children().addClass('active');
                if (next.next().text().trim() === '»') {
                    next.next().children().addClass('disabled');
                }
                pageNumber = next.text();
            } else {
                // 如果是第一頁，則把上一頁按鈕設為disabled
                if ($(this).parent().prev().text().trim() === '«') {
                    $(this).parent().prev().children().addClass('disabled');
                }
                // 如果是最後一頁，則把下一頁按鈕設為disabled
                if ($(this).parent().next().text().trim() === '»') {
                    $(this).parent().next().children().addClass('disabled');
                }
                $(this).addClass('active');
            }
            // 5. 顯示頁面
            loadPageContent(pageNumber, id);
        });
    
        // 預設點擊第一頁
        $('.pagination .page-item').eq(1).find('a').trigger('click');
    });
}

// 假設點擊第一頁的行為是載入頁面的內容
function loadPageContent(pageNumber, id) {
    /**
     * table 要顯示內容的公式為: 
     * start: (pageNumber-1)*pagelength
     * end:pageNumber*pagelength
     * */

    $(`#${id}`).find('tr').addClass('d-none');
    var start = (pageNumber-1) * $('#page_length').val();
    var end = pageNumber * $('#page_length').val();
    for (var i = start; i < end; i++) {
        $(`#${id}`).find('tr').eq(i).removeClass('d-none');
    }
}

