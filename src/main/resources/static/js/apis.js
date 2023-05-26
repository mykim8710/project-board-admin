async function callFindAllAdminUsersApi() {
    let searchKeyword = document.getElementById('searchInput').value;
    let page = currentPage;
    let size = document.getElementById('adminUsers-size').value;
    let sort = sortCondition.concat(",").concat(sortDirection);

    const res = await fetch(`/api/v1/admin-users?searchKeyword=${searchKeyword}&page=${page}&size=${size}&sort=${sort}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callDuplicateCheckAdminUserInfoApi(type, keyword) {
    const res = await fetch(`/api/v1/admin-users/duplicate-check?type=${type}&keyword=${keyword}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        }
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callCreateAdminUserApi(adminUserCreateDto) {
    const res = await fetch(`/api/v1/admin-users`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(adminUserCreateDto)
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callFindAllArticlesApi() {
    let searchKeyword = document.getElementById('searchInput').value;
    let page = currentPage;
    let size = document.getElementById('articles-size').value;
    let sort = sortCondition.concat(",").concat(sortDirection);

    const res = await fetch(`/api/v1/management/articles?searchKeyword=${searchKeyword}&page=${page}&size=${size}&sort=${sort}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callFindOneArticleApi(articleId) {
    const res = await fetch(`/api/v1/management/articles/${articleId}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callRemoveArticleApi(articleId) {
    const res = await fetch(`/api/v1/management/articles/${articleId}`, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callFindAllArticleCommentsApi() {
    let searchKeyword = document.getElementById('searchInput').value;
    let page = currentPage;
    let size = document.getElementById('articleComments-size').value;
    let sort = sortCondition.concat(",").concat(sortDirection);

    const res = await fetch(`/api/v1/management/article-comments?searchKeyword=${searchKeyword}&page=${page}&size=${size}&sort=${sort}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callFindOneArticleCommentApi(articleCommentId) {
    const res = await fetch(`/api/v1/management/article-comments/${articleCommentId}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callRemoveArticleCommentApi(articleCommentId) {
    const res = await fetch(`/api/v1/management/article-comments/${articleCommentId}`, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callFindAllHashtagsApi() {
    let searchKeyword = document.getElementById('searchInput').value;
    let page = currentPage;
    let size = document.getElementById('hashtags-size').value;
    let sort = sortCondition.concat(",").concat(sortDirection);

    const res = await fetch(`/api/v1/management/hashtags?searchKeyword=${searchKeyword}&page=${page}&size=${size}&sort=${sort}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callAddHashtagApi(hashtagCreateDto) {
    const res = await fetch(`/api/v1/management/hashtags`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(hashtagCreateDto)
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callRemoveHashtagApi(hashtagId) {
    const res = await fetch(`/api/v1/management/hashtags/${hashtagId}`, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}

async function callFindAllServiceUsersApi() {
    let searchKeyword = document.getElementById('searchInput').value;
    let page = currentPage;
    let size = document.getElementById('serviceUsers-size').value;
    let sort = sortCondition.concat(",").concat(sortDirection);

    const res = await fetch(`/api/v1/management/service-users?searchKeyword=${searchKeyword}&page=${page}&size=${size}&sort=${sort}`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}