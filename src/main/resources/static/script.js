$('document').ready(function () {

    let sessionEmail = null;
    let sessionId = null;
    let sessionRole = null;

    function renderHome() {
        $.get('cookie', function (res) {
            if (!res.includes('JSESSIONID')) {
                $.get('templates/landing_sign_log.html', function (data) {
                    $('#css-sheet').attr('href', 'landing.css')
                    $('body').html('');
                    $('body').append(data);
                })
            } else {
                setSessionID(res);

                $.get('templates/navbar.html', function (data) {
                    $('#css-sheet').attr('href', 'personalized.css')
                    $('body').html('');
                    $('body').append(data);
                })
                getDashboard()
            }
        })
    }

    function setSessionID(res) {
        let cookies = res.split(',')
        let sessionCookie = null;
        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i]
            if (cookie.includes('JSESSIONID')) {
                sessionCookie = cookie.substring(11);
            }
        }

        $.get(`cookie/${sessionCookie}`, function (res) {
            sessionEmail = res;
        })
    }

    $('body').on('click', '#btn-login', function () {
        renderLogin();
    })

    function renderLogin() {
        $.get('templates/login.html', function (data) {
            $('#css-sheet').attr('href', 'login.css')
            $('body').html('');
            $('body').append(data);
        })
    }


    $('body').on('click', '#btn-signup', function () {
        renderSignup();
    })

    function renderSignup() {
        $.get('templates/signup_1.html', function (data) {
            $('#css-sheet').attr('href', 'login.css')
            $('body').html('');
            $('body').append(data);
        })
    }

    function renderSignupBis(email) {
        $.get('templates/signup_2.html', function (data) {
            $('#css-sheet').attr('href', 'login.css')
            $('body').html('');
            $('body').append(data);
            $('#email').val(email);
        })
    }

    $('body').on('click', '#verify-email', function () {
        let email = $('#email').val();
        $.get(`user/checkEmail/${email}`, function (res) {
            console.log(res)
            if (res) {
                renderSignupBis(email)
            } else {
                alert('L\'email inserita è già presente nel sistema');
                renderSignup();
            }
        })
    })

    $('body').on('click', '#btn-submit-signup', function () {

        const user = {
            email: $('#email').val(),
            password: $('#password').val(),
            tax_code: $('#tax_code').val(),
            name: $('#name').val(),
            surname: $('#surname').val(),
            phone: $('#phone').val(),
            address: $('#address').val(),
            role: "USER"
        }

        $.ajax({
            url: '/signup',
            type: 'POST',
            processData: false,
            contentType: 'false',
            data: JSON.stringify(user),
            contentType: 'application/json',
            success: function (res) {
                if (res) {
                    renderLogin();
                } else {
                    alert('Codice fiscale già presente nel sistema')
                }
            }
        })
    })

    // ---------  DASHBOARD ----------

    function getDashboard() {

        $.get('templates/dashboard_main.html', function (data) {
            $('#main').html('');
            $('#main').append(data);

            $.get(`user/email/${sessionEmail}`, function (res) {
                sessionId = res.id;
                sessionRole = res.role;
                $('h6:contains("[USERNAME]")')
                    .text(`${res.name} ${res.surname}`)
                    .attr('id-author', sessionId);
                $('#bio').text(`${res.bio}`)
                if (res.image != null) {
                    $('img[SRC="[PROFILEPIC]"]').attr('src', res.image)
                } else {
                    $('img[SRC="[PROFILEPIC]"]').attr('src',
                        'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                }
                $('#btn-add-post')
                    .attr('id-author', sessionId);
                $('#profile, #profile3').attr('id-author', sessionId)
                renderGallery(sessionId);
                renderPosts();
                renderSuggested();
            })

        })

    }

    function renderSuggested() {
        $.get('user', function (res) {
            $('#suggested').html('')
            for (let i = 0; i < (res.length > 5 ? 5 : res.length); i++) {
                $.get('templates/suggested.html', function (data) {
                    $('#suggested').append(data)
                    if (res[i].image != null) {
                        $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].image)
                    } else {
                        $('img[SRC="[PROFILEPIC]"]').attr('src',
                            'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                    }
                    $('h6:contains("[USERNAME]")')
                        .text(`${res[i].name} ${res[i].surname}`)
                        .attr('id-author', res[i].id);
                })
            }
        })
    }

    function renderGallery(id) {
        $.get(`post/${id}`, function (res) {
            $('#gallery').html('')
            for (let i = 0; i < res.length; i++) {
                if (res[i].image != null) {
                    $.get('templates/gallery.html', function (data) {
                        $('#gallery').append(data)
                        $('img[SRC="[URLIMAGE]"]').attr('src', res[i].image);
                    })
                }
            }
        })
    }

    function renderPosts() {

        $.get('post', function (res) {
            $('#dashboard').html('');

            for (let i = 0; i < res.length; i++) {
                if (res[i].user.id == `${sessionId}` || sessionRole == 'ADMIN') {
                    $.get('templates/owned_post.html', function (data) {
                        $('#dashboard').append(data);
                        if (res[i].user.image != null) {
                            $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].user.image)
                        } else {
                            $('img[SRC="[PROFILEPIC]"]').attr('src',
                                'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                        }
                        $('h6:contains("[USERNAME]")')
                            .text(`${res[i].user.name} ${res[i].user.surname}`)
                            .attr('id-author', res[i].user.id);
                        $('span:contains("[DATE]")').text(`${res[i].content_time}`);
                        $('p:contains("[CONTENT]")').text(`${res[i].content}`);
                        $('button[ID-POST="[ID]"], div[ID-POST="[IDPOST]"], ' +
                            '.btn-share[ID-POST="[IDPOST]"], .share-text-field[ID-POST="[IDPOST]"]')
                            .attr('id-post', res[i].id);
                        // $('').attr('id-post', res[i].id);
                        if (res[i].image != null) {
                            $.get('templates/img_post.html', function (data) {
                                $(`.append-img[ID-POST="${res[i].id}"]`).append(data);
                                $('img[SRC="[URLIMAGE]"]').attr('src', res[i].image);
                            })

                        }
                    })
                } else {
                    $.get('templates/post.html', function (data) {
                        $('#dashboard').append(data);
                        if (res[i].user.image != null) {
                            $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].user.image)
                        } else {
                            $('img[SRC="[PROFILEPIC]"]').attr('src',
                                'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                        }
                        $('h6:contains("[USERNAME]")')
                            .text(`${res[i].user.name} ${res[i].user.surname}`)
                            .attr('id-author', res[i].user.id);
                        $('span:contains("[DATE]")').text(`${res[i].content_time}`);
                        $('p:contains("[CONTENT]")').text(`${res[i].content}`);
                        $('button[ID-POST="[ID]"], div[ID-POST="[IDPOST]"], ' +
                            '.btn-share[ID-POST="[IDPOST]"], .share-text-field[ID-POST="[IDPOST]"]')
                            .attr('id-post', res[i].id);
                        if (res[i].image != null) {
                            $.get('templates/img_post.html', function (data) {
                                $(`.append-img[ID-POST="${res[i].id}"]`).append(data);
                                $('img[SRC="[URLIMAGE]"]').attr('src', res[i].image);
                            })

                        }
                    })
                }
                renderComments(res[i].id)
            }
        })
    }

    function renderComments(idPost) {
        $.get(`comment/${idPost}`, function (res) {
            $(`div[ID-POST="${idPost}"]`).html('');
            if (res.length != 0) {
                for (let i = 0; i < res.length; i++) {
                    if (res[i].user.id == `${sessionId}` || sessionRole == 'ADMIN') {
                        $.get('templates/owned_comment.html', function (data) {
                            $(`.comments[ID-POST="${idPost}"]`).append(data);
                            if (res[i].user.image != null) {
                                $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].user.image)
                            } else {
                                $('img[SRC="[PROFILEPIC]"]').attr('src',
                                    'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                            }
                            $('p:contains("[COMMENTAUTHOR]")')
                                .text(`${res[i].user.name} ${res[i].user.surname} - ${res[i].content_time}`);
                            $('p:contains("[COMMENTCONTENT]")').text(`${res[i].content} `);
                            $('button[ID-COMMENT="[IDCOMMENT]"]').attr('id-comment', res[i].id)
                        })
                    }else {
                        $.get('templates/comment.html', function (data) {
                            $(`.comments[ID-POST="${idPost}"]`).append(data);
                            if (res[i].user.image != null) {
                                $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].user.image)
                            } else {
                                $('img[SRC="[PROFILEPIC]"]').attr('src',
                                    'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                            }
                            $('p:contains("[COMMENTAUTHOR]")')
                                .text(`${res[i].user.name} ${res[i].user.surname} - ${res[i].content_time}`);
                            $('p:contains("[COMMENTCONTENT]")').text(`${res[i].content} `);
                        })
                    }
                }
            }
        })
    }

    renderHome();

    $('body').on('click', '#home, #home2, #home3', function () {
        getDashboard();
        $('#switch1').addClass('active');
        $('#switch1').removeClass('notification-trigger');
        $('#switch2').addClass('notification-trigger');
        $('#switch2').removeClass('active');
        $('#switch3').addClass('notification-trigger');
        $('#switch3').removeClass('active');
    })

    $('body').on('click', '#user-list, #user-list2, #user-list3', function () {
        getUserList();
        $('#switch3').addClass('active');
        $('#switch3').removeClass('notification-trigger');
        $('#switch1').removeClass('active');
        $('#switch1').addClass('msg-trigger');
        $('#switch2').addClass('notification-trigger');
        $('#switch2').removeClass('active');
    })

    $('body').on('click', '#profile, #profile3, .author', function () {
        let id = $(this).attr('id-author')
        getProfile(id);
        $('#switch2').addClass('active');
        $('#switch2').removeClass('notification-trigger');
        $('#switch1').addClass('notification-trigger');
        $('#switch1').removeClass('active');
        $('#switch3').addClass('notification-trigger');
        $('#switch3').removeClass('active');
    })

    function getUserList() {

        $.get('templates/userlist_main.html', function (data) {
            $('#main').html('');
            $('#main').append(data);
        })
        renderUsers();
    }

    function renderUsers() {
        $.get('user', function (res) {
            for (let i = 0; i < res.length; i++) {
                $.get('templates/user_card.html', function (data) {
                    $('.friends-list').append(data);
                    if (res[i].image != null) {
                        $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].image)
                    } else {
                        $('img[SRC="[PROFILEPIC]"]').attr('src',
                            'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                    }
                    $('div[ID-AUTHOR="[IDAUTHOR]"]')
                        .attr('id-author', res[i].id);
                    $('h6:contains("[USERNAME]")')
                        .text(`${res[i].name} ${res[i].surname}`)
                        .attr('id-author', res[i].id);

                })
            }
        })
    }


    $('body').on('click', '#btn-add-post', function () {
        let form = $('#image-post')[0];
        let data = new FormData(form);
        saveImage(data)
    })

    function saveImage(data) {
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: 'post/upload',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (res) {
                const post = {
                    user: {
                        id: $('#btn-add-post').attr('id-author')
                    },
                    content: $('#add-content').val(),
                }
                if (res != "") {
                    post.image = res
                }
                addPost(post);
            },
            error: function (e) {
                console.log(e)
            }
        })
    }

    function addPost(post) {

        $.ajax({
            url: 'post',
            type: 'POST',
            processData: false,
            contentType: 'false',
            data: JSON.stringify(post),
            contentType: 'application/json',
            success: function (res) {
                if (res) {
                    $('#textbox').fadeOut(300);
                    $('.modal-backdrop').fadeOut(300);
                    renderPosts();
                    $('#add-content').val('');
                    $('#file-input').val('');
                } else {
                    alert('C\'è stato un errore nel database')
                }
            }
        })
    }

    function getProfile(id) {
        $('#main').html('');
        if (id == sessionId || sessionRole == 'ADMIN') {
            $.get('templates/owned_profile.html', function (data) {
                $('#main').append(data);
                $.get(`user/${id}`, function (res) {
                    $('h4:contains("[USERNAME]")')
                        .text(`${res.name} ${res.surname}`);
                    $('#bio').text(`${res.bio}`)
                    $('#user-info').attr('id-author', res.id)
                    $('#btn-form-edit-user').attr('id-author', res.id)
                    if (res.image != null) {
                        $('img[SRC="[PROFILEPIC]"]').attr('src', res.image)
                    } else {
                        $('img[SRC="[PROFILEPIC]"]').attr('src',
                            'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                    }

                    $('#btn-add-post')
                        .attr('id-author', sessionId);
                    renderGallery(id);
                    renderUserPosts(id);
                    renderSuggested();
                })
            })
        } else {
            $.get('templates/profile.html', function (data) {
                $('#main').append(data);
                $.get(`user/${id}`, function (res) {
                    $('h4:contains("[USERNAME]")')
                        .text(`${res.name} ${res.surname}`);
                    $('#bio').text(`${res.bio}`)
                    $('#user-info').attr('id-author', res.id)
                    if (res.image != null) {
                        $('img[SRC="[PROFILEPIC]"]').attr('src', res.image)
                    } else {
                        $('img[SRC="[PROFILEPIC]"]').attr('src',
                            'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                    }

                    $('#btn-add-post')
                        .attr('id-author', sessionId);
                    renderGallery(id);
                    renderUserPosts(id);
                    renderSuggested();
                })
            })
        }
    }

    function renderUserPosts(id) {
        $.get(`post/${id}`, function (res) {
            $('#dashboard').html('');

            for (let i = 0; i < res.length; i++) {
                if (id == `${sessionId}` || sessionRole == 'ADMIN') {
                    $.get('templates/owned_post.html', function (data) {
                        $('#dashboard').append(data);
                        if (res[i].user.image != null) {
                            $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].user.image)
                        } else {
                            $('img[SRC="[PROFILEPIC]"]').attr('src',
                                'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                        }
                        $('h6:contains("[USERNAME]")')
                            .text(`${res[i].user.name} ${res[i].user.surname}`)
                            .attr('id-author', res[i].user.id);
                        $('span:contains("[DATE]")').text(`${res[i].content_time}`);
                        $('p:contains("[CONTENT]")').text(`${res[i].content}`);
                        $('button[ID-POST="[ID]"]').attr('id-post', res[i].id);
                        $('div[ID-POST="[IDPOST]"]').attr('id-post', res[i].id);
                        if (res[i].image != null) {
                            $.get('templates/img_post.html', function (data) {
                                $(`.append-img[ID-POST="${res[i].id}"]`).append(data);
                                $('img[SRC="[URLIMAGE]"]').attr('src', res[i].image);
                            })

                        }
                    })
                } else {
                    $.get('templates/post.html', function (data) {
                        $('#dashboard').append(data);
                        if (res[i].user.image != null) {
                            $('img[SRC="[PROFILEPIC]"]').attr('src', res[i].user.image)
                        } else {
                            $('img[SRC="[PROFILEPIC]"]').attr('src',
                                'https://cdn.hackaday.io/images/resize/600x600/2399971484117200492.png')
                        }
                        $('h6:contains("[USERNAME]")')
                            .text(`${res[i].user.name} ${res[i].user.surname}`)
                            .attr('id-author', res[i].user.id);
                        $('span:contains("[DATE]")').text(`${res[i].content_time}`);
                        $('p:contains("[CONTENT]")').text(`${res[i].content}`);
                        $('button[ID-POST="[ID]"]').attr('id-post', res[i].id);
                        $('div[ID-POST="[IDPOST]"]').attr('id-post', res[i].id);
                        if (res[i].image != null) {
                            $.get('templates/img_post.html', function (data) {
                                $(`.append-img[ID-POST="${res[i].id}"]`).append(data);
                                $('img[SRC="[URLIMAGE]"]').attr('src', res[i].image);
                            })

                        }
                    })
                }
                renderComments(res[i].id)
            }
        })
    }

    $('body').on('click', '#user-info', function () {
        let id = $(this).attr('id-author')
        renderInfo(id)
    })

    function renderInfo(id) {
        $.get(`user/${id}`, function (res) {
            $.get('templates/profilo_info_user.html', function (data) {
                if (id == `${sessionId}` || sessionRole == 'ADMIN') {
                    $('#empty-share-post').html('<div id="dashboard"></div>')
                }
                $('#dashboard').html('')
                $('#dashboard').append(data)
                $('#name-surname').text(`${res.name} ${res.surname}`)
                $('#email').text(`${res.email}`)
                $('#tax-code').text(`${res.tax_code}`)
                $('#address').text(`${res.address}`)
                $('#phone').text(`${res.phone}`)

            })
        })
    }

    $('body').on('click', '#btn-form-edit-user', function () {
        let id = $(this).attr('id-author')
        renderEditInfo(id)
    })

    function renderEditInfo(id) {
        $.get(`user/${id}`, function (res) {
            $.get('templates/update_user.html', function (data) {
                $('#empty-share-post').html('<div id="dashboard"></div>')
                $('#dashboard').html('')
                $('#dashboard').append(data)
                $('#name').val(`${res.name}`)
                $('#surname').val(`${res.surname}`)
                $('#edit-bio').val(`${res.bio == null ? '' : res.bio}`)
                $('#address').val(`${res.address}`)
                $('#phone').val(`${res.phone}`)
                $('#btn-edit-user').attr('id-author', id)

            })
        })
    }

    $('body').on('click', '#btn-edit-user', function (res) {
        let form = $('#image-post')[0];
        let data = new FormData(form);
        saveUserImage(data)
    })

    function saveUserImage(data) {
        $.ajax({
            url: 'user/upload',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (res) {
                const user = {
                    id: $('#btn-edit-user').attr('id-author'),
                    name: $('#name').val(),
                    surname: $('#surname').val(),
                    bio: $('#edit-bio').val(),
                    address: $('#address').val(),
                    phone: $('#phone').val()
                }
                if (res != "") {
                    user.image = res
                }
                editUser(user);
            },
            error: function (e) {
                console.log(e)
            }
        })
    }

    function editUser(user){
        $.ajax({
            url: 'user',
            type: 'PUT',
            processData: false,
            contentType: 'false',
            data: JSON.stringify(user),
            contentType: 'application/json',
            success: function (res) {
                if (res) {
                    getProfile(user.id);
                } else {
                    alert('C\'è stato un errore nel database')
                }
            }
        })
    }

    $('body').on('click', '.btn-delete-post', function () {
        idPost = $(this).attr('id-post');
        deletePost(idPost);
    })

    function deletePost(idPost) {
        $.ajax({
            url: `post/${idPost}`,
            type: 'DELETE',
            success: function (msg) {
                if (msg) {
                    renderPosts();
                } else {
                    alert('C\'è stato un errore nel sistema')
                }
            }
        })
    }

    $('body').on('click', '.btn-edit-post', function () {
        let idPost = $(this).attr('id-post');
        modalEditPost(idPost);

    })

    function modalEditPost(idPost) {
        $.get(`post/post/${idPost}`, function (res) {
            $('#edit-content').text(`${res.content}`);
            $('#btn-edit-post').attr('id-post', `${res.id}`);
        })
    }



    $('body').on('click', '#btn-edit-post', function () {
        const post = {
            id: $(this).attr('id-post'),
            content: $('#edit-content').val()
        }
        editPost(post);
    })

    function editPost(post) {
        $.ajax({
            url: 'post',
            type: 'PUT',
            data: JSON.stringify(post),
            contentType: 'application/json',
            success: function (msg) {
                if (msg) {
                    $('#textbox').fadeOut(300);
                    $('.modal-backdrop').fadeOut(300);
                    renderPosts();
                } else {
                    alert('C\'è stato un errore nel sistema')
                }
            }
        })
    }

    $('body').on('click', '.btn-share', function(){
        let idPost = $(this).attr('id-post')
        const comment = {
            post: {
                id: idPost
            },
            user: {
                id: sessionId
            },
            content: $(`.share-text-field[ID-POST="${idPost}"]`).val()
        }
        if(comment.content != ""){
            addComment(comment);
        }
    })

    function addComment(comment){
        $.ajax({
            url: 'comment',
            type: 'POST',
            data: JSON.stringify(comment),
            contentType: 'application/json',
            success: function (msg) {
                if (msg) {
                    renderPosts();
                } else {
                    alert('C\'è stato un errore nel sistema')
                }
            }
        })
    }

    $('body').on('click', '.btn-delete-comment', function(){
        let idComment = $(this).attr('id-comment')
        deleteComment(idComment)
    })

    function deleteComment(idComment){
        $.ajax({
            url: `comment/${idComment}`,
            type: 'DELETE',
            success: function (msg) {
                if (msg) {
                    renderPosts();
                } else {
                    alert('C\'è stato un errore nel sistema')
                }
            }
        })
    }

    $('body').on('click', '.btn-edit-comment', function () {
        let idComment = $(this).attr('id-comment');
        modalEditComment(idComment);

    })

    function modalEditComment(idComment) {
        $.get(`comment/comment/${idComment}`, function (res) {
            $('#comment-content').text(`${res.content}`);
            $('#btn-edit-comment').attr('id-comment', `${res.id}`);
        })
    }

    $('body').on('click', '#btn-edit-comment', function () {
        const comment = {
            id: $(this).attr('id-comment'),
            content: $('#comment-content').val()
        }
        editComment(comment);
    })

    function editComment(comment) {
        $.ajax({
            url: 'comment',
            type: 'PUT',
            data: JSON.stringify(comment),
            contentType: 'application/json',
            success: function (msg) {
                if (msg) {
                    $('#modal-edit-comment').fadeOut(300);
                    $('.modal-backdrop').fadeOut(300);
                    renderPosts();
                } else {
                    alert('C\'è stato un errore nel sistema')
                }
            }
        })
    }

})
