const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

// myModal.addEventListener('shown.bs.modal', () => myInput.focus())
Vue.createApp({
    data(){
        return{
            cliente : [],
            tipoPrestamo : [],
            cuotas : [],
            cuotasSeleccionada : '',
            prestamoSeleccionado : '',
            monto : '',
            destino : '',
            cuentas : [],
            montoCuotas : '',
            destino : '',
            modal : ''
            
        }
    },
    created(){
        // axios.get(`http://localhost:8080/api/clients/current`)
        axios.get(`https://home-banking-mh.herokuapp.com/api/clients/current`)
        .then(response => {
            this.cliente = response.data
            this.cuentas = this.cliente.accounts
        })

    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        mostrarCuotas(value){
            // axios.get(`http://localhost:8080/api/loans/${value}`)
            axios.get(`https://home-banking-mh.herokuapp.com/api/loans/${value}`)
            .then(response => {
                this.tipoPrestamo = response.data
                this.cuotas = this.tipoPrestamo.payments
                this.prestamoSeleccionado = this.tipoPrestamo.name
            })
        },
        solicitarPrestamo(){
            // axios.post('http://localhost:8080/api/clients/current/loans',{"id" : this.tipoPrestamo.id,"amount" : this.monto,"payments" : this.cuotasSeleccionada,"target" : this.destino})
            axios.post('https://home-banking-mh.herokuapp.com/api/clients/current/loans',{"id" : this.tipoPrestamo.id,"amount" : this.monto,"payments" : this.cuotasSeleccionada,"target" : this.destino})
            .then(response => {
                console.log("Created")
                this.modal = 'modalCreado'
                // window.location.href = "/web/accounts.html"
            })
            .catch(error => {
                console.log("hubo un error")
                this.modal = 'modalError'
            })

        },
        aceptar(){
            window.location.href = "/web/accounts.html"
        }
                    

    },
    computed: {
        calculoCuota(){
            this.montoCuotas = Math.ceil((this.monto*((this.tipoPrestamo.porcentaje/100)+1))/this.cuotasSeleccionada)
        },
        
        

    }
}).mount('#app');
console.log('loan-application.js')