document.addEventListener("DOMContentLoaded", function() {
    const packageSelect = document.getElementById("package");
    const form = document.getElementById('signUpForm');

    // Paketleri doldurmak için API çağrısı
    fetch("http://34.172.128.173/api/package/getAllPackages")
        .then(response => {
            if (!response.ok) {
                throw new Error(`Paketleri alırken hata: ${response.status} ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Paket verileri:', data);
            data.forEach(pkg => {
                console.log(pkg);
                const option = document.createElement("option");
                option.value = pkg.packageId;
                option.textContent = `${pkg.packageName} - ${pkg.amountMinutes} min - ${pkg.price} $ - ${pkg.amountData} MB - ${pkg.amountSms} SMS - ${pkg.period} period`;
                packageSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Paketleri alırken hata oluştu:', error);
        });

    // Kayıt formunu göndermek için API çağrısı
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const name = document.getElementById('name').value;
        const surname = document.getElementById('surname').value;
        const email = document.getElementById('email').value;
        const phoneNumber = document.getElementById('phoneNumber').value;
        const password = document.getElementById('password').value;
        const securityKey = document.getElementById('securityKey').value;
        const packageId = document.getElementById('package').value;

        fetch('http://34.172.128.173/api/auth/registerWithPackage', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                MSISDN: phoneNumber,
                NAME: name,
                SURNAME: surname,
                EMAIL: email,
                PASSWORD: password,
                SECURITY_KEY: securityKey,
                PACKAGE_ID: packageId
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Kayıt isteği hatası: ${response.status} ${response.statusText}`);
                }
                return response.json(); // Yanıtı JSON olarak işliyoruz
            })
            .then(data => {
                console.log('Parsed JSON Data:', data);
                if (data.success) {
                    alert('Kayıt BAŞARILI!');
                    window.location.href = '/pixcell/static/Login/login.html';
                } else {
                    alert('Kayıt BAŞARISIZ: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Hata:', error);
                alert('Bir hata oluştu. Lütfen tekrar deneyiniz.');
            });
    });
});
