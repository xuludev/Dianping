# DaZhongDianPing Web Crawler

![dianping](http://www.dpfile.com/s/c/app/main/index-header/i/sprite.122340b14b6d989d8548edb59bd3a93c.png)

## Introduction

* parse web page, extract info we want and save them at local computer, user city as folder, and type as file's name

* _Dev_ multi-thread spider is developing...

* _framework_ is developing...

## Parse rule
> 按消费类型：
> 
> http://m.dianping.com/shoplist/2/r/0/c/10/s/s_-1
  http://m.dianping.com/shoplist/2/r/0/c/15/s/s_-1
  http://m.dianping.com/shoplist/2/r/0/c/35/s/s_-1
  http://m.dianping.com/shoplist/2/r/0/c/85/s/s_-1
  _http://m.dianping.com/shoplist/{城市id}/r/0/c/{消费类型id}/s/s_-1_

---
>按商区名称：
>
> http://m.dianping.com/shoplist/2/r/14/c/0/s/s_-1
  http://m.dianping.com/shoplist/2/r/15/c/0/s/s_-1
  http://m.dianping.com/shoplist/2/r/5951/c/0/s/s_-1
  http://m.dianping.com/shoplist/{城市id}/r/{商区id}/c/0/s/s_-1

---

> 综合:
> 
> http://m.dianping.com/shoplist/{城市id}/r/{商区id}/c/{消费类型id}/s/s_-1

---

> 商户小类：
> 
> http://m.dianping.com/shoplist/1/r/0/c/10/s/s_-1
  http://m.dianping.com/shoplist/1/r/0/c/101/s/s_-1
  http://m.dianping.com/shoplist/1/r/0/c/113/s/s_-1
  http://m.dianping.com/shoplist/1/r/0/c/132/s/s_-1
  http://m.dianping.com/shoplist/1/r/0/c/{消费小类id}/s/s_-1
