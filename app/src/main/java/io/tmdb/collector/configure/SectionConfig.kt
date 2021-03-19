package io.tmdb.collector.configure

/**
 * configure home page section
 * @author: pickerx
 * @date:2021/3/10 6:45 PM
 */
class SectionConfig {

    private val mSectionConfig: Int = 0x7

    companion object {
        // 最流行
        const val SECTION_HEADER_VIEW_PAGE = 0x1

        // 流行的明星
        const val SECTION_POPULAR_PERSON = 0x2

        // 每日，每周趋势
        const val SECTION_TRENDING = 0x4

        // 即将上线
        const val SECTION_UPCOMING = 0x8
        // 评分最高
        const val SECTION_TOP_RATED = 0x16
    }

    /**
     * support the section configure
     *
     * @param config
     */
    fun has(config: Int) = mSectionConfig and config != 0

    fun add(config: Int) = mSectionConfig or config
}