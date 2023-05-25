window.onload = function () {
    $('#sidebarMenu .nav-link').removeClass('active');
    $(`#sidebarMenu #service-management-${pageName}`).addClass('active');

    switch (pageName) {
        case "articles" :
            findAllArticles();
            break;
        case "articleComments" :
            findAllArticleComments();
            break;
        case "hashtags" :
            findAllHashtags();
            break;
        case "serviceUsers" :
            findAllServiceUsers();
            break;
    }

    document.getElementById(`${pageName}-size`).addEventListener('change', e => {
        switch (pageName) {
            case "articles" :
                findAllArticles();
                break;
            case "articleComments" :
                findAllArticleComments();
                break;
            case "hashtags" :
                findAllHashtags();
                break;
            case "serviceUsers" :
                findAllServiceUsers();
                break;
        }
    });

    document.getElementById(`searchInput`).addEventListener('input', e => {
        currentPage = 0;
        switch (pageName) {
            case "articles" :
                findAllArticles();
                break;
            case "articleComments" :
                findAllArticleComments();
                break;
            case "hashtags" :
                findAllHashtags();
                break;
            case "serviceUsers" :
                findAllServiceUsers();
                break;
        }
    });
}

function sort(selectedSortCondition) {
    currentPage = 0;
    sortCondition = selectedSortCondition;
    sortDirection = sortDirection === "DESC" ? "ASC" : "DESC";
    switch (pageName) {
        case "articles" :
            findAllArticles();
            break;
        case "articleComments" :
            findAllArticleComments();
            break;
        case "hashtags" :
            findAllHashtags();
            break;
        case "serviceUsers" :
            findAllServiceUsers();
            break;
    }
}

function pagination(paginationResponse) {
    let nowPage    = paginationResponse.page;
    let startPage  = paginationResponse.startPage;
    let endPage    = paginationResponse.endPage;
    let totalPage  = paginationResponse.totalPage;
    let totalCount = paginationResponse.totalCount;
    let prevPage   = paginationResponse.prevPage;
    let nextPage   = paginationResponse.nextPage;

    document.getElementById(`${pageName}-current-page`).innerText = nowPage;
    document.getElementById(`${pageName}-total-page`).innerText = totalPage;
    document.getElementById(`${pageName}-total-count`).innerText = totalCount;

    let paginationArea = document.getElementById(`${pageName}-pagination`);
    paginationArea.innerText = "";

    const ulTag = document.createElement('ul');
    ulTag.className = "pagination justify-content-center";

    let html  = `<li class="page-item"><a class="page-link" onclick="movePage(${startPage})">First</a></li>`;
        html += `<li class="page-item"><a class="page-link" onclick="movePage(${prevPage})">Previous</a></li>`;

        for (let i = startPage; i <= endPage; i++) {
            if (nowPage == i) {
                html += `<li class="page-item active" aria-current="page"><a class="page-link" onclick="movePage(${i})">${i}</a></li>`;
            } else {
                html += `<li class="page-item"><a class="page-link" onclick="movePage(${i})">${i}</a></li>`;
            }
        }

        html += `<li class="page-item"><a class="page-link" onclick="movePage(${nextPage})">Next</a></li>`;
        html += `<li class="page-item"><a class="page-link" onclick="movePage(${endPage})">Last</a></li>`;

    ulTag.innerHTML = html;
    paginationArea.appendChild(ulTag);
}

function movePage(pageNumber) {
    if(currentPage === (pageNumber-1)) {
        return;
    }

    currentPage = (pageNumber-1);
    switch (pageName) {
        case "articles" :
            findAllArticles();
            break;
        case "articleComments" :
            findAllArticleComments();
            break;
        case "hashtags" :
            findAllHashtags();
            break;
        case "serviceUsers" :
            findAllServiceUsers();
            break;
    }
}
