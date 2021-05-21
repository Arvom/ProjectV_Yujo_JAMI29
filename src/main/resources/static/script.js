$('document').ready(function () {

    let sessionEmail = null;
    let sessionId = null;

    function renderHome() {
        $.get('cookie', function (res) {
            if (!res.includes('JSESSIONID')) {
                $.get('landing_sign_log.html', function (data) {
                    $('#css-sheet').attr('href', 'landing.css')
                    $('body').html('');
                    $('body').append(data);
                })
            } else {
                setSessionID(res);

                $.get('navbar.html', function (data) {
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
        $.get('login.html', function (data) {
            $('#css-sheet').attr('href', 'login.css')
            $('body').html('');
            $('body').append(data);
        })
    }


    $('body').on('click', '#btn-signup', function () {
        renderSignup();
    })

    function renderSignup() {
        $.get('signup_1.html', function (data) {
            $('#css-sheet').attr('href', 'login.css')
            $('body').html('');
            $('body').append(data);
        })
    }

    function renderSignupBis(email) {
        $.get('signup_2.html', function (data) {
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

        $.get('dashboard_main.html', function (data) {
            $('#main').html('');
            $('#main').append(data);

            $.get(`user/email/${sessionEmail}`, function (res) {
                sessionId = res.id;
                $('h6:contains("[USERNAME]")')
                    .text(`${res.name} ${res.surname}`)
                    .attr('id-author', sessionId);
                $('#bio').text(`Telefono: ${res.phone} Indirizzo: ${res.address}`)

                $('#btn-add-post')
                    .attr('id-author', sessionId);
                renderGallery();
                renderPosts();
                renderSuggested();
            })

        })

    }

    function renderSuggested() {
        $.get('user', function (res) {
            $('#suggested').html('')
            for (let i = 0; i < (res.length > 5 ? 5 : res.length); i++) {
                $.get('suggested.html', function (data) {
                    $('#suggested').append(data)
                    $('h6:contains("[USERNAME]")')
                        .text(`${res[i].name} ${res[i].surname}`)
                        .attr('id-author', sessionId);
                })
            }
        })
    }

    function renderGallery() {
        $.get(`post/${sessionId}`, function (res) {
            $('#gallery').html('')
            for (let i = 0; i < res.length; i++) {
                if (res[i].image != null) {
                    $.get('gallery.html', function (data) {
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
                if (res[i].user.id == `${sessionId}`) {
                    $.get('owned_post.html', function (data) {
                        $('#dashboard').append(data);
                        $('h6:contains("[USERNAME]")')
                            .text(`${res[i].user.name} ${res[i].user.surname}`)
                            .attr('id-author', res[i].user.id);
                        $('span:contains("[DATE]")').text(`${res[i].content_time}`);
                        $('p:contains("[CONTENT]")').text(`${res[i].content}`);
                        $('button[ID-POST="[ID]"]').attr('id-post', res[i].id);
                        $('div[ID-POST="[IDPOST]"]').attr('id-post', res[i].id);
                        if (res[i].image != null) {
                            $.get('img_post.html', function (data) {
                                $(`.append-img[ID-POST="${res[i].id}"]`).append(data);
                                $('img[SRC="[URLIMAGE]"]').attr('src', res[i].image);
                            })

                        }
                    })
                } else {
                    $.get('post.html', function (data) {
                        $('#dashboard').append(data);
                        $('h6:contains("[USERNAME]")')
                            .text(`${res[i].user.name} ${res[i].user.surname}`)
                            .attr('id-author', res[i].user.id);
                        $('span:contains("[DATE]")').text(`${res[i].content_time}`);
                        $('p:contains("[CONTENT]")').text(`${res[i].content}`);
                        $('button[ID-POST="[ID]"]').attr('id-post', res[i].id);
                        $('div[ID-POST="[IDPOST]"]').attr('id-post', res[i].id);
                        if (res[i].image != null) {
                            $.get('img_post.html', function (data) {
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
                    $.get('comment.html', function (data) {
                        $(`.comments[ID-POST="${idPost}"]`).append(data);
                        $('p:contains("[COMMENTAUTHOR]")')
                            .text(`${res[i].user.name} ${res[i].user.surname} - ${res[i].content_time}`);
                        $('p:contains("[COMMENTCONTENT]")').text(`${res[i].content} `);
                    })
                }
            }
        })
    }

    renderHome();

    $('body').on('click','#home, #home2, #home3', function () {
        getDashboard();
        $('#switch1').addClass('active');
        $('#switch1').removeClass('notification-trigger');
        $('#switch2').addClass('notification-trigger');
        $('#switch2').removeClass('active');
        $('#switch3').addClass('notification-trigger');
        $('#switch3').removeClass('active');
    })

    $('body').on('click','#user-list, #user-list2, #user-list3', function () {
        getUserList();
        $('#switch3').addClass('active');
        $('#switch3').removeClass('notification-trigger');
        $('#switch1').removeClass('active');
        $('#switch1').addClass('msg-trigger');
        $('#switch2').addClass('notification-trigger');
        $('#switch2').removeClass('active');
    })

    $('body').on('click','#profile, #profile3', function () {
        getProfile();
        $('#switch2').addClass('active');
        $('#switch2').removeClass('notification-trigger');
        $('#switch1').addClass('notification-trigger');
        $('#switch1').removeClass('active');
        $('#switch3').addClass('notification-trigger');
        $('#switch3').removeClass('active');
    })

    function getUserList() {

        $.get('userlist_main.html', function (data) {
            $('#main').html('');
            $('#main').append(data);
        })
        renderUsers();
    }

    function renderUsers() {
        $.get('user', function (res) {
            for (let i = 0; i < res.length; i++) {
                $.get('user_card.html', function (data) {
                    $('.friends-list').append(data);
                    $('div[ID-AUTHOR="[IDAUTHOR]"]')
                        .attr('id-author', res[i].id);
                    $('h6:contains("[USERNAME]")')
                        .text(`${res[i].name} ${res[i].surname}`)
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

    function getProfile() {
        $('#main').html('');
        $.get('profile.html', function (data) {
            $('#main').append(data);
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
        $.get(`post/${idPost}`, function (res) {
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

})
