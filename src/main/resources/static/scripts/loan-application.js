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
            modal : '',
            create : false,
            mensajeError : 'hola',
            montoMax: 0
            
        }
    },
    created(){
        // axios.get(`http://localhost:8080/api/clients/current`)
        axios.get(`https://home-banking-mh.herokuapp.com/api/clients/current`)
        .then(response => {
            this.cliente = response.data
            this.cuentas = this.cliente.accounts.filter(cuenta => cuenta.enable)
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
                this.montoMax = this.formatoNumero(this.tipoPrestamo.maxAmount)
            })
        },
        solicitarPrestamo(){
            // axios.post('http://localhost:8080/api/clients/current/loans',{"id" : this.tipoPrestamo.id,"amount" : this.monto,"payments" : this.cuotasSeleccionada,"target" : this.destino})
            axios.post('https://home-banking-mh.herokuapp.com/api/clients/current/loans',{"id" : this.tipoPrestamo.id,"amount" : this.monto,"payments" : this.cuotasSeleccionada,"target" : this.destino})
            .then(response => {
                this.create = true
                window.location.href = "/web/accounts.html"
            })
            .catch(error => {
                if (this.cuotasSeleccionada === ''){
                    this.mensajeError = 'No seleccionó la cantidad de cuotas'
                }else
                if (this.prestamoSeleccionado === ''){
                    this.mensajeError = 'No seleccionó un tipo de prestamo'
                }else
                if (this.monto === ''){
                    this.mensajeError = 'No indicó el monto'
                }else
                if (this.destino === ''){
                    this.mensajeError = 'No indicó una cuenta de destino'
                }else {
                    this.mensajeError = 'Alcanzo el limite de prestamo '
                }
            })

        },
        aceptar(){
            window.location.href = "/web/accounts.html"
        },
        formatoNumero(number){
            const exp = /(\d)(?=(\d{3})+(?!\d))/g
            const rep = '$1.'
            let arr = number.toString().split('.')
            arr[0] = arr[0].replace(exp,rep)
            return arr[1] ? arr.join(','): arr[0]
          }
                    

    },
    computed: {
        calculoCuota(){
            this.montoCuotas = Math.ceil((this.monto*((this.tipoPrestamo.porcentaje/100)+1))/this.cuotasSeleccionada)
        },
        
        

    }
}).mount('#app');
console.log('loan-application.js')