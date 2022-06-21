const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

// myModal.addEventListener('shown.bs.modal', () => myInput.focus())
Vue.createApp({
    data(){
        return{
            cuenta : '',
            origen : '',
            destino : '',
            monto : 0.0,
            descripcion : '',
            cliente : [],
            cuentas : []
        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                this.cuentas = this.cliente.accounts

            })


    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        crearTransaccion(){
            if(this.cuenta=='tercer'){
                this.cuenta == 'VIN'+this.cuenta
            }
            axios.post('http://localhost:8080/api/clients/current/transactions',`amount=${this.monto}&description=${this.descripcion}&origin=${this.origen}&target=${this.destino}`)
                .then(response => {
                    console.log("Created")
                })
                .catch(error = console.log("hubo un error"))
        },
        aceptar(){
            window.location.href = "/web/accounts.html"
        }

    },
    computed: {

    }
}).mount('#app');
console.log('transfers.js')