const urlParams = new URLSearchParams(window.location.search);
const myParam = urlParams.get('id')

Vue.createApp({
    data(){
        return{
            cuenta : [],
            id : myParam
        }
    },
    created(){
        // axios.get(`http://localhost:8080/api/accounts/${myParam}`)
        axios.get(`/api/accounts/${myParam}`)
            .then(response => {
                this.cuenta = response.data
            })

    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        formatoNumero(number){
            number = number.toFixed(2)
            const exp = /(\d)(?=(\d{3})+(?!\d))/g
            const rep = '$1.'
            let arr = number.toString().split('.')
            arr[0] = arr[0].replace(exp,rep)
            return arr[1] ? arr.join(','): arr[0]
        }
    },
    computed: {

    }
}).mount('#app');