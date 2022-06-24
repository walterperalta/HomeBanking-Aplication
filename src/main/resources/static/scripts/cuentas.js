Vue.createApp({
    data(){
        return{
            cliente : [],
            prestamos : [],
            erro : false,
            cuentas : [],
            destino : '',
            create : false,
            cuentasRestantes: [],
            cuentaEliminar: '',
            create : false,
            tipoCuenta : '',
            cuentasFiltradas : []

        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
        // axios.get(`https://home-banking-mh.herokuapp.com/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                this.prestamos = this.cliente.loans
                this.cuentas = this.cliente.accounts.filter(cuenta => cuenta.enable)
                // let loader = document.querySelector('#loader-container')
                // loader.classList.add('loader-desactive')
            })


    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        crearCuenta(){
            axios.post('/api/clients/current/accounts',`type=${this.tipoCuenta}`)
            // axios.post('/api/clients/current/accounts')
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
            axios.patch(`http://localhost:8080/api/accounts/${param}`,`target=${this.destino}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            // axios.patch(`https://home-banking-mh.herokuapp.com/api/accounts/${param}`)
            .then(response => {
                console.log('ok!')
                this.create = true
                window.location.href = "/web/accounts.html";
            })

        },
        filtrarCuentas(param){
            this.cuentasFiltradas = this.cuentas.filter(cuenta => cuenta.number != param)
        }

    },
    computed: {
        
    }
}).mount('#app');