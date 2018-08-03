import re
import requests
from bs4 import BeautifulSoup

if __name__ == "__main__":
    site = 'http://discozoowiki.com/wiki/Animals'

    response = requests.get(site)

    soup = BeautifulSoup(response.text, 'html.parser')
    img_tags = soup.find_all('img')

    urls = [img['src'] for img in img_tags]

    for url in urls:
        filename = re.search(r'/([\w_-]+[.](jpg|gif|png))$', url)
        if filename is not None:
            with open('..\\resources\\animals\\{}'.format(filename.group(1)), 'wb') as f:
                url = 'http://discozoowiki.com{}'.format(url)
                print(url)
                response = requests.get(url)
                f.write(response.content)
