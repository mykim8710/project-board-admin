
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