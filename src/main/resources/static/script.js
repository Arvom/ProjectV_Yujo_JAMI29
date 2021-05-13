$('document').ready(function () {

    function getDashboard() {

        $.get('dashboard_main.html', function (data) {
            $('#main').html('');
            $('#main').append(data);

            // TODO ID DA SESSIONE -----------------

            $('button[ID-AUTHOR="[IDAUTHOR]"]')
                .attr('id-author', 1);
        })
        renderPosts()
    }

    function renderPosts(){

        $.get('post', function (res) {
            $('#dashboard').html('');
            for (let i = 0; i < res.length; i++) {
                $.get('post.html', function (data) {

                    $('#dashboard').append(data);
                    $('h6:contains("[USERNAME]")')
                        .text(`${res[i].name} ${res[i].surname}`)
                        .attr('id-author', res[i].id_user);
                    $('span:contains("[DATE]")').text(`${res[i].content_time}`);
                    $('p:contains("[CONTENT]")').text(`${res[i].content}`);
                    $('button[ID-POST="[ID]"]').attr('id-post', res[i].id);
                })
            }
        })
    }

    getDashboard();

    $('#home, #home2').click(function () {
        getDashboard();
        $('#switch1').addClass('active');
        $('#switch1').removeClass('notification-trigger');
        $('#switch2').addClass('notification-trigger');
        $('#switch2').removeClass('active');
        $('#switch3').addClass('notification-trigger');
        $('#switch3').removeClass('active');
    })

    $('#user-list, #user-list2').click(function () {
        getUserList();
        $('#switch3').addClass('active');
        $('#switch3').removeClass('notification-trigger');
        $('#switch1').removeClass('active');
        $('#switch1').addClass('msg-trigger');
        $('#switch2').addClass('notification-trigger');
        $('#switch2').removeClass('active');
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
/*
    $('#main').on('click', '#btn-add-post', function () {
        const post = {
            content: $('#add-content').val()
        }
        let idAuthor = $(this).attr('id-author');
        addPost(post, idAuthor);
    })

    function addPost(post, idAuthor) {
        $.ajax({
            url: `post/${idAuthor}`,
            type: 'POST',
            data: JSON.stringify(post),
            contentType: 'application/json',
            success: function (msg) {
                if (msg) {
                    $('#textbox').fadeOut(300);
                    $('.modal-backdrop').fadeOut(300);
                    renderPosts();
                    $('#add-content').val('');
                } else {
                    alert('C\'è stato un errore nel sistema')
                }
            }
        })
    }

    $('#profile').click(function () {
        getProfile();
        $('#switch2').addClass('active');
        $('#switch2').removeClass('notification-trigger');
        $('#switch1').addClass('notification-trigger');
        $('#switch1').removeClass('active');
        $('#switch3').addClass('notification-trigger');
        $('#switch3').removeClass('active');
    })

    function getProfile(){
        $('#main').html('');
        $.get('profile.html', function (data) {
            $('#main').append(data);
        })
    }

    $('#main').on('click', '.btn-delete-post', function () {
        idPost = $(this).attr('id-post');
        deletePost(idPost);
    })

    function deletePost(idPost){
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

    $('#main').on('click', '.btn-edit-post', function () {
        let idPost = $(this).attr('id-post');
        modalEditPost(idPost);

    })

    function modalEditPost(idPost){
        $.get(`post/${idPost}`, function(res){
                $('#edit-content').text(`${res.content}`);
                $('#btn-edit-post').attr('id-post',`${res.id}`);
        })
    }

    $('#main').on('click', '#btn-edit-post', function () {
        const post = {
            id: $(this).attr('id-post'),
            content: $('#edit-content').val()
        }
        editPost(post);
    })

    function editPost(post){
        $.ajax({
            url: 'post',
            type: 'PUT',
            data: JSON.stringify(post),
            contentType: 'application/json',
            success: function(msg) {
                if(msg){
                    $('#textbox').fadeOut(300);
                    $('.modal-backdrop').fadeOut(300);
                    renderPosts();
                }else{
                    alert('C\'è stato un errore nel sistema')
                }
            }
        })
    }

 */
})