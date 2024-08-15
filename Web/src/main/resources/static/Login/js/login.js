document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('myForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const msisdn = document.getElementById('phoneNumber').value;
        const password = document.getElementById('password').value;


        const data = {
            msisdn: msisdn,
            password: password
        };

        fetch('http://34.172.128.173/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {

                    window.location.href = '/pixcell/static/UserInformation/userInformation.html';
                } else {

                    alert('Login failed: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred. Please try again later.');
            });
    });
});
