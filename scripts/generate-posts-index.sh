#!/bin/bash

# Script to generate posts index from markdown files inside /posts directory

set -e  # Exit on any error

echo "Generating posts index... 📝"

mkdir -p ./posts

cat > ./posts/index.json << 'EOF'
{
  "posts": [
EOF

if ! ls ./posts/*.md 1> /dev/null 2>&1; then
    echo "⚠️  No markdown files found in posts directory"
    echo '  ]' >> ./posts/index.json
    echo '}' >> ./posts/index.json
    exit 0
fi

# Count total markdown files
total=$(ls ./posts/*.md 2>/dev/null | wc -l)
count=0

echo "📝 Found $total markdown files"

for file in ./posts/*.md; do
    [ ! -f "$file" ] && continue

    # Extract filename without extension
    filename=$(basename "$file" .md)

    # Extract title from first line (assuming # Title format)
    title=$(head -n1 "$file" | sed 's/^# *//' | tr -d '\r\n')

    # If no title found or it's still a markdown header, use filename
    if [ -z "$title" ] || [[ "$title" == "#"* ]]; then
        title="$filename"
    fi

    if [[ "$OSTYPE" == "darwin"* ]]; then
        date=$(stat -f "%Sm" -t "%Y-%m-%d" "$file")
    else
        date=$(date -r "$file" "+%Y-%m-%d")
    fi

    count=$((count + 1))

    echo "  📄 Processing: $filename"

    cat >> ./posts/index.json << EOF
    {
      "id":  "$(basename "$file")_$date",
      "title": "$title",
      "publishedAt": "$date",
      "file": "$(basename "$file")"
EOF

    if [ $count -lt $total ]; then
        echo "    }," >> ./posts/index.json
    else
        echo "    }" >> ./posts/index.json
    fi
done

cat >> ./posts/index.json << 'EOF'
  ]
}
EOF

echo "✅ Generated index with $count posts"
echo "📋 Index file created at: posts/index.json"

if [ "${SHOW_OUTPUT:-false}" = "true" ]; then
    echo "📄 Generated content:"
    cat ./posts/index.json
fi