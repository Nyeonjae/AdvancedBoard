const $main = document.getElementById('main');


const {
    ClassicEditor,
    AccessibilityHelp,
    Alignment,
    Autoformat,
    AutoImage,
    Autosave,
    BlockQuote,
    BlockToolbar,
    Bold,
    CloudServices,
    Code,
    CodeBlock,
    Essentials,
    FontBackgroundColor,
    FontColor,
    FontFamily,
    FontSize,
    FullPage,
    GeneralHtmlSupport,
    Heading,
    Highlight,
    HorizontalLine,
    HtmlComment,
    HtmlEmbed,
    ImageBlock,
    ImageCaption,
    ImageInline,
    ImageInsert,
    ImageInsertViaUrl,
    ImageResize,
    ImageStyle,
    ImageTextAlternative,
    ImageToolbar,
    ImageUpload,
    Indent,
    IndentBlock,
    Italic,
    Link,
    LinkImage,
    List,
    ListProperties,

    MediaEmbed,
    Mention,
    Paragraph,

    PasteFromOffice,
    RemoveFormat,
    SelectAll,
    ShowBlocks,
    SimpleUploadAdapter,
    SourceEditing,
    SpecialCharacters,
    SpecialCharactersArrows,
    SpecialCharactersCurrency,
    SpecialCharactersEssentials,
    SpecialCharactersLatin,
    SpecialCharactersMathematical,
    SpecialCharactersText,
    Strikethrough,
    Style,
    Subscript,
    Superscript,
    Table,
    TableCaption,
    TableCellProperties,
    TableColumnResize,
    TableProperties,
    TableToolbar,
    TextPartLanguage,
    TextTransformation,
    Title,
    TodoList,
    Underline,
    Undo
} = CKEDITOR;



const editorConfig = {
    toolbar: {
        items: [
            'undo',
            'redo',
            '|',
            'sourceEditing',
            'showBlocks',
            'textPartLanguage',
            '|',
            'heading',
            'style',
            '|',
            'fontSize',
            'fontFamily',
            'fontColor',
            'fontBackgroundColor',
            '|',
            'bold',
            'italic',
            'underline',
            'strikethrough',
            'subscript',
            'superscript',
            'code',
            'removeFormat',
            '|',
            'specialCharacters',
            'horizontalLine',
            'link',
            'insertImage',
            'insertImageViaUrl',
            'mediaEmbed',
            'insertTable',
            'highlight',
            'blockQuote',
            'codeBlock',
            'htmlEmbed',
            '|',
            'alignment',
            '|',
            'bulletedList',
            'numberedList',
            'todoList',
            'outdent',
            'indent'
        ],
        shouldNotGroupWhenFull: true
    },
    plugins: [
        AccessibilityHelp,
        Alignment,
        Autoformat,
        AutoImage,
        Autosave,
        BlockQuote,
        BlockToolbar,
        Bold,
        CloudServices,
        Code,
        CodeBlock,
        Essentials,
        FontBackgroundColor,
        FontColor,
        FontFamily,
        FontSize,
        FullPage,
        GeneralHtmlSupport,
        Heading,
        Highlight,
        HorizontalLine,
        HtmlComment,
        HtmlEmbed,
        ImageBlock,
        ImageCaption,
        ImageInline,
        ImageInsert,
        ImageInsertViaUrl,
        ImageResize,
        ImageStyle,
        ImageTextAlternative,
        ImageToolbar,
        ImageUpload,
        Indent,
        IndentBlock,
        Italic,
        Link,
        LinkImage,
        List,
        ListProperties,

        MediaEmbed,
        Mention,
        Paragraph,

        PasteFromOffice,
        RemoveFormat,
        SelectAll,
        ShowBlocks,
        SimpleUploadAdapter,
        SourceEditing,
        SpecialCharacters,
        SpecialCharactersArrows,
        SpecialCharactersCurrency,
        SpecialCharactersEssentials,
        SpecialCharactersLatin,
        SpecialCharactersMathematical,
        SpecialCharactersText,
        Strikethrough,
        Style,
        Subscript,
        Superscript,
        Table,
        TableCaption,
        TableCellProperties,
        TableColumnResize,
        TableProperties,
        TableToolbar,
        TextPartLanguage,
        TextTransformation,
        Title,
        TodoList,
        Underline,
        Undo
    ],
    blockToolbar: [
        'fontSize',
        'fontColor',
        'fontBackgroundColor',
        '|',
        'bold',
        'italic',
        '|',
        'link',
        'insertImage',
        'insertTable',
        '|',
        'bulletedList',
        'numberedList',
        'outdent',
        'indent'
    ],
    fontFamily: {
        supportAllValues: true
    },
    fontSize: {
        options: [10, 12, 14, 'default', 18, 20, 22],
        supportAllValues: true
    },
    heading: {
        options: [
            {
                model: 'paragraph',
                title: 'Paragraph',
                class: 'ck-heading_paragraph'
            },
            {
                model: 'heading1',
                view: 'h1',
                title: 'Heading 1',
                class: 'ck-heading_heading1'
            },
            {
                model: 'heading2',
                view: 'h2',
                title: 'Heading 2',
                class: 'ck-heading_heading2'
            },
            {
                model: 'heading3',
                view: 'h3',
                title: 'Heading 3',
                class: 'ck-heading_heading3'
            },
            {
                model: 'heading4',
                view: 'h4',
                title: 'Heading 4',
                class: 'ck-heading_heading4'
            },
            {
                model: 'heading5',
                view: 'h5',
                title: 'Heading 5',
                class: 'ck-heading_heading5'
            },
            {
                model: 'heading6',
                view: 'h6',
                title: 'Heading 6',
                class: 'ck-heading_heading6'
            }
        ]
    },
    htmlSupport: {
        allow: [
            {
                name: /^.*$/,
                styles: true,
                attributes: true,
                classes: true
            }
        ]
    },
    image: {
        toolbar: [
            'toggleImageCaption',
            'imageTextAlternative',
            '|',
            'imageStyle:inline',
            'imageStyle:wrapText',
            'imageStyle:breakText',
            '|',
            'resizeImage'
        ]
    },
    initialData:
        $main['content'].value,
    language: 'ko',
    link: {
        addTargetToExternalLinks: true,
        defaultProtocol: 'https://',
        decorators: {
            toggleDownloadable: {
                mode: 'manual',
                label: 'Downloadable',
                attributes: {
                    download: 'file'
                }
            }
        }
    },
    list: {
        properties: {
            styles: true,
            startIndex: true,
            reversed: true
        }
    },
    mention: {
        feeds: [
            {
                marker: '@',
                feed: [
                    /* See: https://ckeditor.com/docs/ckeditor5/latest/features/mentions.html */
                ]
            }
        ]
    },
    menuBar: {
        isVisible: true
    },
    placeholder: 'Type or paste your content here!',
    style: {
        definitions: [
            {
                name: 'Article category',
                element: 'h3',
                classes: ['category']
            },
            {
                name: 'Title',
                element: 'h2',
                classes: ['document-title']
            },
            {
                name: 'Subtitle',
                element: 'h3',
                classes: ['document-subtitle']
            },
            {
                name: 'Info box',
                element: 'p',
                classes: ['info-box']
            },
            {
                name: 'Side quote',
                element: 'blockquote',
                classes: ['side-quote']
            },
            {
                name: 'Marker',
                element: 'span',
                classes: ['marker']
            },
            {
                name: 'Spoiler',
                element: 'span',
                classes: ['spoiler']
            },
            {
                name: 'Code (dark)',
                element: 'pre',
                classes: ['fancy-code', 'fancy-code-dark']
            },
            {
                name: 'Code (bright)',
                element: 'pre',
                classes: ['fancy-code', 'fancy-code-bright']
            }
        ]
    },
    table: {
        contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties']
    },
};


ClassicEditor.create($main['content'], editorConfig).then((editor) => {
    $main.onsubmit = (e) => {
        e.preventDefault();
        //console.log($main['title'].value) // 글 제목
        //console.log(editor.getData()); // 글 내용
        if ($main['title'].value === '') {
            return;
        }
        if ($main['nickname'].value === '') {
            return;
        }
        if ($main['password'].value === ''){
            return;
        }
        if ($main['password'].value !== $main['passwordCheck'].value) {
            alert('입력하신 비밀번호가 서로 일치하지 않습니다.');
            $main['passwordCheck'].focus();
            return;
        }
        const url = new URL(location.href);
        const xhr = new XMLHttpRequest();
        const formData = new FormData();
        formData.append('index', url.searchParams.get('index')); // 주소에 있는 index
        formData.append('oldPassword', url.searchParams.get('password')); // 주소에 있는 password ( 이거는 옛 비밀 번호임 . 현재 게시글의 비밀번호와 일치하는지 확인하는 용도 )
        formData.append('nickname', $main['nickname'].value); // 닉네임 싣고
        formData.append('password', $main['password'].value); // 비밀번호를 싣고 ( 이거는 신규 비밀번호임. 물론 옛 비밀번호와 같을 수도 있지만 보장이 없음으로 분리하여 생각하여야 함. 검증용도로 사용하지 말 것.
        formData.append('title', $main['title'].value);  // 제목 싣고
        formData.append('content', editor.getData());   //  내용 싣고

        xhr.onreadystatechange = () => {

            if (xhr.readyState !== XMLHttpRequest.DONE) {
                return;
            }
            if (xhr.status < 200 || xhr.status >= 300) {
                alert('게시글을 수정하지 못하였습니다. 잠시 후 다시 시도해 주세요');
                return;
            }
            const response = JSON.parse(xhr.responseText);
            if (response['result'] === true) {
                location.href = `./read?index=${url.searchParams.get('index')}`;
            }
            else {
                alert('게시글을 수정하지 못하였습니다. 잠시 후 다시 시도해 주세요');
            }
        };
        xhr.open('PATCH', './modify');   //  location.href = http://localhost:8080/article/modify?index=3&password=TEST
        xhr.send(formData);                         // ./modift     = http://localhost:8080/article/modify
                                                    // location.href로 하면 도메인의 패스워드와 formDate의 패스워드가 두 번 들어가게 됨
    };
});

