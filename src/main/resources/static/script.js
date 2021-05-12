$('document').ready(function () {

    function getDashboard() {
        $.get('dashboard_main.html', function (data) {
            $('#main').append(data);
        })


        $.get('post', function(res){
            for (let i = 0; i < res.length; i++) {
                const post = {
                    id: 1,
                    idUtente: 7,
                    nome: 'Giulio',
                    cognome: 'Tavano',
                    commento: 'Ariciao so er primo',
                    data: 'bhe ora 12.54'
                }
                $.get('post.html', function (data) {
                    $('#dashboard').append(data);
                    $('h6:contains("[USERNAME]")').text(`${post.nome} ${post.cognome}`);
                })
            }
        })

        const post2 = {
            id: 2,
            idUtente: 7,
            nome: 'Giulio',
            cognome: 'Tavano',
            commento: 'Ariciao so er secondo',
            data: 'bhe ora 13.05'
        }

        $.get('post.html', function (data) {
            $('#dashboard').append(data);
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

    function getUserList() {
        $.get('userlist_main.html', function (data) {
            $('#main').append(data);
        })
        for (let i = 0; i < 15; i++) {
            $.get('user_card.html', function (data) {
                $('.friends-list').append(data);
            })
        }
    }
})
