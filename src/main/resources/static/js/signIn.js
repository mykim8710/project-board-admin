document.getElementById('sign-in-btn').addEventListener('click', e => {
    signIn();
});

document.getElementById('username').addEventListener('keydown', e => {
    if(e.keyCode === 13) {
        signIn();
    }
});

document.getElementById('password').addEventListener('keydown', e => {
    if(e.keyCode === 13) {
        signIn();
    }
});

function signIn() {
    let username = document.getElementById('username').value.trim();
    let password = document.getElementById('password').value.trim();

    if(username === null || username.length === 0 || password === null || password.length === 0) {
        alert("please input username or password");
        return;
    }

    let signInDto = {
        "username" : username,
        "password" : password
    }

    callSignInApi(signInDto).then(response => {
        if(response.status === 200) {
            location.href = response.data;
        }

        if(response.status === 400) {
            alert(response.message);
        }
    });
}

async function callSignInApi(signInDto) {
    const res = await fetch(`/login`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(signInDto)
    }).catch(error => {
        console.log(error);
    });
    return await res.json();
}