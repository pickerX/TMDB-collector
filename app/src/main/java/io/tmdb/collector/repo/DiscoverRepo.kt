package io.tmdb.collector.repo

/**
 * 通过不同类型的数据（例如平均评分，票数，类型和认证）发现电影。
 *
 * @author: pickerx
 * @date:2021/3/12 11:15 AM
 */
class DiscoverRepo {

    suspend fun discover() {
        val discover: Discover
    }


    inner class FullDiscover : Discover {

        override fun where(): String {
            val where = "language=en-US&sort_by=popularity.desc&certification_country=1" +
                    "&certification=1&certification.lte=1&certification.gte=1&include_adult=false" +
                    "&include_video=false&page=1&primary_release_year=1&primary_release_date.gte=1" +
                    "&primary_release_date.lte=1&release_date.gte=1&release_date.lte=1&with_release_type=1" +
                    "&year=1&vote_count.gte=1&vote_count.lte=1&vote_average.gte=1&vote_average.lte=1" +
                    "&with_cast=1&with_crew=1&with_people=1&with_companies=1&with_genres=1&without_genres=1" +
                    "&with_keywords=1&without_keywords=1&with_runtime.gte=1&with_runtime.lte=1&with_original_language=1" +
                    "&with_watch_providers=1&watch_region=1"
            return where
        }
    }

    /**
     * 发现算法接口
     */
    interface Discover {
        /**
         * 返回匹配字符串
         */
        fun where(): String
    }

}