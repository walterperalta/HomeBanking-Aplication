Vue.createApp({
    data(){
        return{
            cliente : [],
            prestamos : [],
            erro : false,
            cuentas : []

        }
    },
    created(){
        axios.get(`https://home-banking-mh.herokuapp.com/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                this.prestamos = this.cliente.loans
                this.cuentas = this.cliente.accounts.filter(cuenta => cuenta.enable)
                let loader = document.querySelector('#loader-container')
                loader.classList.add('loader-desactive')
            })


    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        crearCuenta(tipo){
            axios.post('/api/clients/current/accounts',`tipo=${tipo}`)
                .then(response => {
                    console.log("Created!")
                    window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    console.log("No se puede crear mas cuentas")
                    this.erro = true
                })
        },
        verMovimientos(id){
            window.location.href = `account.html?id=${id}`;
        },
        eliminar(param){
            axios.patch(`https://home-banking-mh.herokuapp.com/api/accounts/${param}`)
            .then(response => {
                console.log('ok!')
                window.location.href = "/web/accounts.html";
            })

        }

    },
    computed: {

    }
}).mount('#app');