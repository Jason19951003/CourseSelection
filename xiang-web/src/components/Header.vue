<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <header class="navbar navbar-expand-lg navbar-light">
        <div class="container-fluid w-75">
            <div class="collapse navbar-collapse d-flex justify-content-between" id="navbarNav">
                <img src="/public/logo.png" alt="" class="logo">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">校園介紹</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown1" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            學術單位
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown1">
                            <li><a class="dropdown-item" href="#">電機工程系</a></li>
                            <li><a class="dropdown-item" href="#">資訊工程系</a></li>
                            <li><a class="dropdown-item" href="#">資訊管理系</a></li>
                            <li><a class="dropdown-item" href="#">企業管理系</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown2" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            招生資訊
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown2">
                            <li><a class="dropdown-item" href="#">申請入學</a></li>
                            <li><a class="dropdown-item" href="#">轉學招生</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown3" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            資訊服務
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown3">
                            <li><a class="dropdown-item" href="#">行政人員</a></li>
                            <li><a class="dropdown-item" href="#">教職人員</a></li>
                            <li><a class="dropdown-item" href="#">在校學生</a></li>
                            <li><a class="dropdown-item" href="#">課程資訊</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </header>
    <main>

    </main>

    <footer>
        
    </footer>
</template>

<script setup lang="ts">
    import { onMounted } from 'vue'
    import { Dropdown } from 'bootstrap'

    onMounted(() => {
        document.querySelectorAll('.dropdown-toggle').forEach(dropdownToggle => {
            new Dropdown(dropdownToggle)
        })
    })
</script>

<style scoped>
    .logo {
        width: 20%;
        height: auto;
    }

    ul li {
        font-size: 24px;
        font-weight: bold;
    }

    .nav-link {
        color: black;
    }
</style>