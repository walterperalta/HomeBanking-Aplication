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
        axios.get(`http://localhost:8080/api/accounts/${myParam}`)
            .then(response => {
                this.cuenta = response.data
            })

    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        }
    },
    computed: {

    }
}).mount('#app');
console.log('cuentaa.js')