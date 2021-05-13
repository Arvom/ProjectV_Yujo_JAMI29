$('document').ready(function () {

    function getDashboard() {
        $.get('dashboard_main.html', function (data) {
            $('#main').append(data);
        })


        $.get('post', function(res){
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

    $('#home').click(function () {
        $('#main').html('');
        getDashboard();
        $('#switch1').addClass('active');
        $('#switch1').removeClass('notification-trigger');
        $('#switch3').addClass('notification-trigger');
        $('#switch3').removeClass('active');
    })

    $('#user-list').click(function () {
        $('#main').html('');
        getUserList();
        $('#switch1').removeClass('active');
        $('#switch1').addClass('msg-trigger');
        $('#switch3').removeClass('notification-trigger');
        $('#switch3').addClass('active');
    })

    $('#user-list2').click(function () {
        $('#main').html('');
        getUserList();
        $('#switch1').removeClass('active');
        $('#switch1').addClass('msg-trigger');
        $('#switch3').removeClass('notification-trigger');
        $('#switch3').addClass('active');
    })

    function getUserList() {
        $.get('userlist_main.html', function (data) {
            $('#main').append(data);
        })
        $.get('user', function(res){
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


})